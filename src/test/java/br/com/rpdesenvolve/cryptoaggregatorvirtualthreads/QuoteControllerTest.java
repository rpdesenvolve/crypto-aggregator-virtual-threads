package br.com.rpdesenvolve.cryptoaggregatorvirtualthreads;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuoteController.class)
class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CryptoService cryptoService;

    @Test
    void testGetQuotes() throws Exception {
        when(cryptoService.fetchQuote("BTC")).thenReturn(new BigDecimal("50000.00"));
        when(cryptoService.fetchQuote("ETH")).thenReturn(new BigDecimal("3000.00"));
        when(cryptoService.fetchQuote("ADA")).thenReturn(new BigDecimal("1.50"));
        when(cryptoService.fetchQuote("SOL")).thenReturn(new BigDecimal("150.00"));
        when(cryptoService.fetchQuote("DOGE")).thenReturn(new BigDecimal("0.20"));

        mockMvc.perform(get("/v1/api/quotes"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.BTC").value(50000.00))
                .andExpect(jsonPath("$.ETH").value(3000.00))
                .andExpect(jsonPath("$.ADA").value(1.50))
                .andExpect(jsonPath("$.SOL").value(150.00))
                .andExpect(jsonPath("$.DOGE").value(0.20));
    }
}