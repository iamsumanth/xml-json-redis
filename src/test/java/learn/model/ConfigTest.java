package learn.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    public void getSubdomainUrls() {
        String urlOne = "http://secureline.tools.avast.com";
        String urlTwo = "http://gf.tools.avast.com";
        Config config = new Config();
        Subdomain subdomainOne = new Subdomain(urlOne);
        Subdomain subdomainTwo = new Subdomain(urlTwo);
        config.setSubdomains(List.of(subdomainOne, subdomainTwo));

        List<String> resultSubdomainUrls = config.getSubdomainUrls();
        List<String> expectedSubdomainUrls = List.of(urlOne, urlTwo);

        assertEquals(expectedSubdomainUrls, resultSubdomainUrls);
    }
}