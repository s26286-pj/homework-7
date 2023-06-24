package pl.pjatk.AverageExchangeRates.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import pl.pjatk.AverageExchangeRates.Exception.NotFoundRateException;

import java.net.UnknownHostException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<String> handleNotFoundMovieException(HttpClientErrorException.NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Response from nbp api: " + ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Response from nbp api: " + e.getLocalizedMessage());
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<String> handleUnknownHostException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Cannot connect to nbp api");
    }

    @ExceptionHandler(NotFoundRateException.class)
    public ResponseEntity<String> handleUnknownHostException(NotFoundRateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cannot calculate rate");
    }
}
