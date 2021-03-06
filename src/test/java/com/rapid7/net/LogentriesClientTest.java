package com.rapid7.net;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LogentriesClientTest {

    private static final String API_HTTP_SERVER = "%s.api.logs.insight.rapid7.com";
    private static final String API_TOKEN_SERVER = "%s.data.logs.insight.rapid7.com";
    private static final String DATAHUB_IP = "127.0.0.1";
    private static final int LE_SSL_PORT = 443;
    private static final int LE_PORT = 80;
    private static final int DATAHUB_PORT = 10000;
    private static final boolean USE_HTTP_PUT = true;
    private static final boolean NOT_USE_HTTP_PUT = false;
    private static final boolean USE_SSL = true;
    private static final boolean NOT_USE_SSL = false;
    private static final boolean USE_DATAHUB = true;
    private static final boolean NOT_USE_DATAHUB = false;

    @Test
    public void testGetAddress() {
        String region = "eu";
        LogentriesClient client = new LogentriesClient(USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "", 0, region);
        String expectedApiServer = String.format(API_HTTP_SERVER, region);
        assertEquals(expectedApiServer  + " should be used for HTTP PUT", client.getAddress(), expectedApiServer);

        String expectedDataServer = String.format(API_TOKEN_SERVER, region);
        LogentriesClient client2 = new LogentriesClient(NOT_USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "", 0, region);
        assertEquals(expectedDataServer + " should be used for Token TCP", client2.getAddress(), expectedDataServer);
    }

    @Test
    public void testGetPort() {
        LogentriesClient client3 = new LogentriesClient(USE_HTTP_PUT, USE_SSL, NOT_USE_DATAHUB, "", 0, "");
        assertEquals("Port 443 should be used for SSL over HTTP", client3.getPort(), LE_SSL_PORT);

        LogentriesClient client4 = new LogentriesClient(USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "", 0, "");
        assertEquals("Port 80 should be used for HTTP PUT", client4.getPort(), LE_PORT);

        LogentriesClient client5 = new LogentriesClient(NOT_USE_HTTP_PUT, USE_SSL, NOT_USE_DATAHUB, "", 0, "");
        assertEquals("Port 443 should be used for SSL over Token TCP", client5.getPort(), LE_SSL_PORT);

        LogentriesClient client6 = new LogentriesClient(NOT_USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "", 0, "");
        assertEquals("Port 80 should be used for Token TCP", client6.getPort(), LE_PORT);
    }

    @Test
    public void testDataHubAddress() {
        String region = "eu";
        String apiHostWithRegion = String.format(API_HTTP_SERVER, region);
        String dataHostWithRegion = String.format(API_TOKEN_SERVER, region);

        LogentriesClient client = new LogentriesClient(USE_HTTP_PUT, USE_SSL, USE_DATAHUB, "127.0.0.1", 10000, region);
        assertEquals("Address 127.0.0.1 should be used over " + apiHostWithRegion, client.getAddress(), DATAHUB_IP);

        LogentriesClient client2 = new LogentriesClient(USE_HTTP_PUT, NOT_USE_SSL, USE_DATAHUB, "127.0.0.1", 10000, region);
        assertEquals("Address 127.0.0.1 should be used over " + apiHostWithRegion, client2.getAddress(), DATAHUB_IP);

        LogentriesClient client3 = new LogentriesClient(NOT_USE_HTTP_PUT, USE_SSL, USE_DATAHUB, "127.0.0.1", 10000, region);
        assertEquals("Address 127.0.0.1 should be used over " + dataHostWithRegion, client3.getAddress(), DATAHUB_IP);

        LogentriesClient client4 = new LogentriesClient(NOT_USE_HTTP_PUT, NOT_USE_SSL, USE_DATAHUB, "127.0.0.1", 10000, region);
        assertEquals("Address 127.0.0.1 should be used over " + dataHostWithRegion, client4.getAddress(), DATAHUB_IP);

        LogentriesClient client5 = new LogentriesClient(USE_HTTP_PUT, USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, region);
        assertNotEquals("Address " + apiHostWithRegion + " should be used over 127.0.0.1", client5.getAddress(), DATAHUB_IP);

        LogentriesClient client6 = new LogentriesClient(USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, region);
        assertNotEquals("Address " + apiHostWithRegion + " should be used over 127.0.0.1", client6.getAddress(), DATAHUB_IP);

        LogentriesClient client7 = new LogentriesClient(NOT_USE_HTTP_PUT, USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, region);
        assertNotEquals("Address " + dataHostWithRegion + " should be used over 127.0.0.1", client7.getAddress(), DATAHUB_IP);

        LogentriesClient client8 = new LogentriesClient(NOT_USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, region);
        assertNotEquals("Address " + dataHostWithRegion + " should be used over 127.0.0.1", client8.getAddress(), DATAHUB_IP);
    }


    @Test
    public void testDataHubPort() {

        LogentriesClient client = new LogentriesClient(USE_HTTP_PUT, USE_SSL, USE_DATAHUB, "127.0.0.1", 10000, "eu");
        assertEquals("Port 10000 should be used over 443", client.getPort(), DATAHUB_PORT);

        LogentriesClient client2 = new LogentriesClient(NOT_USE_HTTP_PUT, NOT_USE_SSL, USE_DATAHUB, "127.0.0.1", 10000, "eu");
        assertEquals("Port 10000 should be used over 80", client2.getPort(), DATAHUB_PORT);

        LogentriesClient client3 = new LogentriesClient(NOT_USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, "eu");
        assertNotEquals("Port 80 should be used over DataHubs port", client3.getPort(), DATAHUB_PORT);

        LogentriesClient client4 = new LogentriesClient(USE_HTTP_PUT, NOT_USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, "eu");
        assertNotEquals("Port 80 should be used over DataHubs port", client4.getPort(), DATAHUB_PORT);

        LogentriesClient client5 = new LogentriesClient(NOT_USE_HTTP_PUT, USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, "eu");
        assertNotEquals("Port 443 should be used over DataHubs port", client5.getPort(), DATAHUB_PORT);

        LogentriesClient client6 = new LogentriesClient(USE_HTTP_PUT, USE_SSL, NOT_USE_DATAHUB, "127.0.0.1", 10000, "eu");
        assertNotEquals("Port 443 should be used over DataHubs port", client6.getPort(), DATAHUB_PORT);
    }
}
