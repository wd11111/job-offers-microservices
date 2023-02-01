package com.wd.scheduler;

import com.wd.clients.httpclient.HttpClient;
import com.wd.clients.offer.OfferClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {"delay.hours=PT2S"})
@SpringJUnitConfig(SchedulingConfiguration.class)
class HttpOfferSchedulerTest {

    @SpyBean
    private HttpOfferScheduler httpOfferScheduler;

    @MockBean
    HttpClient httpClient;

    @MockBean
    OfferClient offerClient;

    @Test
    public void whenWaitOneSecond_thenScheduledIsCalledAtLeastTenTimes() {
        await()
                .atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> verify(httpOfferScheduler, atLeast(2)).saveOffersFromHttpService());
    }
}



