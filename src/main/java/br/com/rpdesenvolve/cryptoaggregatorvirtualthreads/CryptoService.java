package br.com.rpdesenvolve.cryptoaggregatorvirtualthreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CryptoService {

    private static final Logger log = LoggerFactory.getLogger(CryptoService.class);

    public BigDecimal fetchQuote(String cryptoSymbol) {
        log.info("Searching for quote for: {}. Thread: {}", cryptoSymbol, Thread.currentThread());

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalArgumentException("Thread was interrupted", ex);
        }

        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(20_000, 60_000)).round(MathContext.DECIMAL32);
    }
}
