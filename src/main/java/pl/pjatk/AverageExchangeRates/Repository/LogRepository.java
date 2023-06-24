package pl.pjatk.AverageExchangeRates.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.AverageExchangeRates.Entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}
