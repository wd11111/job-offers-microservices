package com.wd.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.wd.clients.offer.OfferDto;
import com.wd.httpclient.client.RemoteOfferClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.TestSocketUtils;
import wiremock.org.apache.http.HttpStatus;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

class OfferHttpClientIntegrationTest implements Samples {

    public static final String HEADER = "Content-Type";
    public static final String CONTENT_TYPE = "application/json";
    private static final String PATH = "/offers";

    int port = TestSocketUtils.findAvailableTcpPort();

    private final ObjectMapper objectMapper = new ObjectMapper();
    WireMockServer wireMockServer;

    RemoteOfferClient remoteOfferClient = new OfferHttpClientTestConfig().remoteOfferTestClient("http://localhost", port, PATH, 1000, 1000);

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(options().port(port));
        wireMockServer.start();
        WireMock.configureFor(port);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void should_return_two_job_offers() throws JsonProcessingException {
        List<OfferDto> listOfOffers = List.of(sampleOfferDto1(), sampleOfferDto2());
        String jsonBodyWithTwoOffers = objectMapper.writeValueAsString(listOfOffers);

        WireMock.stubFor(WireMock.get(PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(HEADER, CONTENT_TYPE)
                        .withBody(jsonBodyWithTwoOffers)));
        List<OfferDto> result = remoteOfferClient.getOffers();

        assertThat(result).containsExactlyInAnyOrderElementsOf(listOfOffers);
    }

    @Test
    void should_return_empty_collection() throws JsonProcessingException {
        String nullResponse = objectMapper.writeValueAsString(null);

        WireMock.stubFor(WireMock.get(PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(HEADER, CONTENT_TYPE)
                        .withBody(nullResponse)));
        List<OfferDto> result = remoteOfferClient.getOffers();

        assertThat(result).isEmpty();
    }

    @Test
    void should_fail_with_connection_reset_by_peer() {
        WireMock.stubFor(WireMock.get(PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(HEADER, CONTENT_TYPE)
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        assertThat(remoteOfferClient.getOffers()).isEmpty();
    }

    @Test
    void should_fail_with_empty_response() {
        WireMock.stubFor(WireMock.get(PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(HEADER, CONTENT_TYPE)
                        .withFault(Fault.EMPTY_RESPONSE)));

        assertThat(remoteOfferClient.getOffers()).isEmpty();
    }

    @Test
    void should_fail_with_malformed() {
        WireMock.stubFor(WireMock.get(PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(HEADER, CONTENT_TYPE)
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

        assertThat(remoteOfferClient.getOffers()).isEmpty();
    }

    @Test
    void should_fail_with_random() {
        WireMock.stubFor(WireMock.get(PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(HEADER, CONTENT_TYPE)
                        .withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

        assertThat(remoteOfferClient.getOffers()).isEmpty();
    }

    @Test
    void should_return_zero_job_offers_when_response_is_delayed() throws JsonProcessingException {
        List<OfferDto> listOfOffers = List.of(sampleOfferDto1(), sampleOfferDto2());
        String jsonBodyWithTwoOffers = objectMapper.writeValueAsString(listOfOffers);

        WireMock.stubFor(WireMock.get(PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withHeader(HEADER, CONTENT_TYPE)
                        .withBody(jsonBodyWithTwoOffers)
                        .withFixedDelay(1500)));

        assertThat(remoteOfferClient.getOffers()).isEmpty();
    }

}

