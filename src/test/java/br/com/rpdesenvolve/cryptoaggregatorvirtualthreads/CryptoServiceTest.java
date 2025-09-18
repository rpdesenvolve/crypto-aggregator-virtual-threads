package br.com.rpdesenvolve.cryptoaggregatorvirtualthreads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CryptoServiceTest {

    private final CryptoService cryptoService = new CryptoService();

    @Test
    void testFetchQuoteReturnsBigDecimal() {
        BigDecimal quote = cryptoService.fetchQuote("BTC");
        assertThat(quote).isNotNull();
        assertThat(quote).isInstanceOf(BigDecimal.class);
    }

    @Test
    void testFetchQuoteThrowsExceptionOnInterruption() {
        Thread.currentThread().interrupt();
        assertThrows(IllegalArgumentException.class, () -> cryptoService.fetchQuote("BTC"));
        assertThat(Thread.currentThread().isInterrupted()).isTrue();
    }
}