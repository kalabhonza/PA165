package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Currency;



/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */

public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) throws ExternalServiceFailureException {
        logger.trace("Convert " + sourceCurrency + " to " + targetCurrency);
        if(sourceCurrency == null || targetCurrency == null || sourceAmount == null){
            throw new IllegalArgumentException("Parameter is null");
        }
        try{
            BigDecimal exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if(exchangeRate == null){
                logger.warn("Exchange rate " + sourceCurrency.getDisplayName() + " / " + targetCurrency.getDisplayName() + " not found.");
                throw new UnknownExchangeRateException("Currency is unnown. ");
            }
            return sourceAmount.multiply(exchangeRate);
        } catch (ExternalServiceFailureException e) {
            logger.error("External service failure exception ", e);
            throw new ExternalServiceFailureException("External service failure exception ", e);
        }
    }
}
