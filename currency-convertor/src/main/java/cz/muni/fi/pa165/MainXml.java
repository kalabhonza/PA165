package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        CurrencyConvertor curr = context.getBean(CurrencyConvertor.class);
        Currency EUR = Currency.getInstance("EUR");
        Currency CZK = Currency.getInstance("CZK");
        BigDecimal OneEurInCzk = curr.convert(EUR, CZK, new BigDecimal("1"));
        System.out.println(OneEurInCzk);
    }
}
