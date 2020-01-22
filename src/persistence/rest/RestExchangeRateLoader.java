package persistence.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import model.Currency;
import model.ExchangeRate;
import persistence.ExchangeRateLoader;

public class RestExchangeRateLoader implements ExchangeRateLoader {

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            return new ExchangeRate(from,to,read(from.getCode(),to.getCode()));
        } catch (IOException ex) {
            return null;
        }
    }

    private double read(String from, String to) throws IOException {
        String line = read(new URL("https://free.currconv.com/api/v7/convert?q="+from+"_"+to+"&compact=ultra&apiKey=faccf36ddaa206b4d6f9"));
        return Double.parseDouble(line.substring(line.indexOf(to)+5, line.indexOf("}")));
    }

    private String read(URL url) throws IOException {
        InputStream is = url.openStream();
        byte[] buffer = new byte[1024];
        int length = is.read(buffer);
        return new String(buffer,0,length);
    }
    
}
