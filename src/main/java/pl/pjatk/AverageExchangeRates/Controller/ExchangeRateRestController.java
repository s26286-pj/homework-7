package pl.pjatk.AverageExchangeRates.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.AverageExchangeRates.Service.ExchangeService;

import java.util.Optional;


@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = @Content(schema = @Schema())
        ),
        @ApiResponse(
                responseCode = "502",
                description = "Internal Server Error",
                content = @Content(schema = @Schema())
        ),
        @ApiResponse(
                responseCode = "504",
                description = "Error on connection to external service",
                content = @Content(schema = @Schema())
        )
}
)

@RestController
@RequestMapping("exchange")
public class ExchangeRateRestController {

    ExchangeService exchangeService;

    public ExchangeRateRestController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("{currency}")
    @Operation(summary = "Get average exchange rate rate",
            description = "Get average exchange rate of the currency in number of last days given",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Calculated average",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rates not found",
                            content = @Content(schema = @Schema())
                    )
            })
    ResponseEntity<Double> getExchangeRate(
            @Parameter(description = "3 letters ISO 4217 code of currency", required = true, example = "NOK") @PathVariable("currency") String currency,
            @Parameter(description = "Number of days in with average exchange rate is calculated", example = "4") @RequestParam Optional<Integer> days) {

        Integer daysCount = days.orElse(1);
        Double average = exchangeService.getAverageExchangeRate(currency, daysCount);
        return ResponseEntity.ok().body(average);
    }
}
