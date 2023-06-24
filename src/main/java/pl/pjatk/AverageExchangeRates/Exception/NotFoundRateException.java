package pl.pjatk.AverageExchangeRates.Exception;

public class NotFoundRateException extends RuntimeException{
    public NotFoundRateException() {
    }

    public NotFoundRateException(String message) {
        super(message);
    }
}
