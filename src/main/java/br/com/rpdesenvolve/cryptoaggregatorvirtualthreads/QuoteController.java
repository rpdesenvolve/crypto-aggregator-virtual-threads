package br.com.rpdesenvolve.cryptoaggregatorvirtualthreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/v1/api/quotes")
public class QuoteController {

    private static final Logger log = LoggerFactory.getLogger(QuoteController.class);
    private final CryptoService cryptoService;

    public QuoteController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping
    public Map<String, BigDecimal> getQuotes() {
        List<String> symbols = List.of("BTC", "ETH", "ADA", "SOL", "DOGE");
        log.info("Request received to search for quotes.");

        Map<String, BigDecimal> quotes = new ConcurrentHashMap<>();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            symbols.forEach(symbol ->
                    executor.submit(() -> {
                        BigDecimal price = cryptoService.fetchQuote(symbol);
                        quotes.put(symbol, price);
                    })
            );
        }

        log.info("Quote search complete.");
        return quotes;
    }
}
