package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    Currency eur = Currency.getInstance("EUR");
    Currency czk = Currency.getInstance("CZK");

    @Mock
    private ExchangeRateTable exchangeRateTable;
    private CurrencyConvertor currencyConvertor;

    @Before
    public void init() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(eur, czk)).thenReturn(new BigDecimal("26"));
        assertEquals(new BigDecimal("26.00"), currencyConvertor.convert(eur, czk, new BigDecimal("1.00")));
        assertEquals(new BigDecimal("52.00"), currencyConvertor.convert(eur, czk, new BigDecimal("2.00")));
        assertEquals(new BigDecimal("29.12"), currencyConvertor.convert(eur, czk, new BigDecimal("1.12")));
        assertEquals(new BigDecimal("2.6"), currencyConvertor.convert(eur, czk, new BigDecimal("0.1")));
        assertEquals(new BigDecimal("0"), currencyConvertor.convert(eur, czk, new BigDecimal("0")));
        assertEquals(new BigDecimal("2600"), currencyConvertor.convert(eur, czk, new BigDecimal("100")));
    }

    @Test
    public void testConvertWithNullSourceCurrency() throws ExternalServiceFailureException {
        Currency usd = null;
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(usd, czk, new BigDecimal("10.00"));
    }

    @Test
    public void testConvertWithNullTargetCurrency() throws ExternalServiceFailureException {
        Currency btc = null;
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(eur, btc, new BigDecimal("100.00"));
    }

    @Test
    public void testConvertWithNullSourceAmount() throws ExternalServiceFailureException {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(eur, czk, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(eur, czk)).thenReturn(null);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(eur, czk, new BigDecimal("10.00"));
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(eur,czk)).thenThrow(ExternalServiceFailureException.class);
        expectedException.expect(ExternalServiceFailureException.class);
        currencyConvertor.convert(eur,czk,new BigDecimal("2.00"));
    }

}
