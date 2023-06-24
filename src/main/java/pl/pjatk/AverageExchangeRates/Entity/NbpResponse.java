package pl.pjatk.AverageExchangeRates.Entity;

public class NbpResponse {
    private String table;
    private String currency;
    private String code;
    private Rate[] rates;

    public Rate[] getRates() {
        return rates;
    }
}
