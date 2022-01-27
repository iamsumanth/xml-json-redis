package learn.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import learn.model.Config;
import learn.model.Cookie;
import learn.model.Subdomain;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLParserTest {
    @Test
    public void readSubdomainsFromXMLFile() throws JsonProcessingException {
        String subdomain = "<config>\n" +
                "    <subdomains>\n" +
                "        <subdomain>http://secureline.tools.avast.com</subdomain>\n" +
                "        <subdomain>http://gf.tools.avast.com</subdomain>\n" +
                "    </subdomains>\n" +
                "</config>";

        XMLParser xmlParser = new XMLParser();
        Config resultConfig = xmlParser.read(subdomain);

        Subdomain expectedSubdomainOne = new Subdomain("http://secureline.tools.avast.com");
        Subdomain expectedSubdomainTwo = new Subdomain("http://gf.tools.avast.com");
        Config expectedConfig = new Config();
        expectedConfig.setSubdomains(List.of(expectedSubdomainOne, expectedSubdomainTwo));

        assertEquals(expectedConfig, resultConfig);
    }

    @Test
    public void readCookiesFromXMLFile() throws JsonProcessingException {
        String cookies = "<config>\n" +
                "    <cookies>\n" +
                "        <cookie name=\"dlp-avast\" host=\"amazon\">mmm_amz_dlp_777_ppc_m</cookie>\n" +
                "        <cookie name=\"dlp-avast\" host=\"baixaki\">mmm_bxk_dlp_777_ppc_m</cookie>\n" +
                "    </cookies>\n" +
                "</config>";

        XMLParser xmlParser = new XMLParser();
        Config resultConfig = xmlParser.read(cookies);

        Cookie expectedCookieOne = new Cookie("dlp-avast", "amazon", "mmm_amz_dlp_777_ppc_m");
        Cookie expectedCookieTwo = new Cookie("dlp-avast", "baixaki", "mmm_bxk_dlp_777_ppc_m");

        Config expectedConfig = new Config();
        expectedConfig.setCookies(List.of(expectedCookieOne, expectedCookieTwo));

        assertEquals(expectedConfig, resultConfig);
    }

    @Test
    public void readSubdomainsAndCookiesFromXMLFile() throws JsonProcessingException {
        String cookies = "<config>\n" +
                "    <subdomains>\n" +
                "        <subdomain>http://secureline.tools.avast.com</subdomain>\n" +
                "        <subdomain>http://gf.tools.avast.com</subdomain>\n" +
                "    </subdomains>\n" +
                "\n" +
                "    <cookies>\n" +
                "        <!-- avast -->\n" +
                "        <cookie name=\"dlp-avast\" host=\"amazon\">mmm_amz_dlp_777_ppc_m</cookie>\n" +
                "        <!-- avg -->\n" +
                "        <cookie name=\"dlp-avg\" host=\"vector\">mmm_vct_dlp_779_ppc_m</cookie>\n" +
                "    </cookies>\n" +
                "</config>";

        XMLParser xmlParser = new XMLParser();
        Config resultConfig = xmlParser.read(cookies);

        Subdomain expectedSubdomainOne = new Subdomain("http://secureline.tools.avast.com");
        Subdomain expectedSubdomainTwo = new Subdomain("http://gf.tools.avast.com");

        Cookie expectedCookieOne = new Cookie("dlp-avast", "amazon", "mmm_amz_dlp_777_ppc_m");
        Cookie expectedCookieTwo = new Cookie("dlp-avg", "vector", "mmm_vct_dlp_779_ppc_m");

        Config expectedConfig = new Config();
        expectedConfig.setSubdomains(List.of(expectedSubdomainOne, expectedSubdomainTwo));
        expectedConfig.setCookies(List.of(expectedCookieOne, expectedCookieTwo));

        assertEquals(expectedConfig, resultConfig);
    }
}