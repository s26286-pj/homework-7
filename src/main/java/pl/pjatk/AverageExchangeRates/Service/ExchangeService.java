package pl.pjatk.AverageExchangeRates.Service;

import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.pjatk.AverageExchangeRates.Entity.Log;
import pl.pjatk.AverageExchangeRates.Entity.NbpResponse;
import pl.pjatk.AverageExchangeRates.Entity.Rate;
import pl.pjatk.AverageExchangeRates.Exception.NotFoundRateException;
import pl.pjatk.AverageExchangeRates.Repository.LogRepository;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ExchangeService {

    private LogRepository logRepository;
    private RestTemplate restTemplate;

    public ExchangeService(ApplicationContext applicationContext, LogRepository logRepository) {
        this.restTemplate = applicationContext.getBean(RestTemplate.class);
        this.logRepository = logRepository;
    }

    private String buildQuery(String currency, LocalDate dateFrom, LocalDate dateTo) {
        return "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" + dateFrom.toString() + "/" + dateTo.toString() + "/??format=json";
    }

    private Double calculateAverageFromRates(Rate rates[]) {
        int count = rates.length;
        if (count == 0) {
            throw new NotFoundRateException();
        }
        Double sum = .0;
        for (Rate rate : rates) {
            sum = sum + rate.getMid();
        }
        Double average = sum / count;
        return average;
    }

    public Double getAverageExchangeRate(String currency, Integer days) {
        LocalDate dateTo = LocalDate.now();
        LocalDate dateFrom = dateTo.minusDays(days);
        Date now = new Date();
        String query = buildQuery(currency, dateFrom, dateTo);

        ResponseEntity<NbpResponse> response = this.restTemplate.getForEntity(query, NbpResponse.class);
        Rate rates[] = response.getBody().getRates();
        Double average = this.calculateAverageFromRates(rates);

        Log log = new Log(currency, days, average, now);
        this.logRepository.save(log);

        return average;
    }
}
