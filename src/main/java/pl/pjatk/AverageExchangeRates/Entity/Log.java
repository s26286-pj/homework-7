package pl.pjatk.AverageExchangeRates.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;
    private Integer days;
    private Double value;

    public Log() {

    }

    public Log(String currency, Integer days, Double value, Date date) {
        this.currency = currency;
        this.days = days;
        this.value = value;
        this.date = date;
    }


    private Date date;

}
