package learn.redis.publisher;

import learn.model.Config;
import learn.model.Cookie;
import learn.model.Subdomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RedisMessagePublisherTest {

    private StringRedisTemplate redisTemplate;
    private ListOperations redisListOperations;
    private RedisMessagePublisher redisMessagePublisher;

    @BeforeEach
    public void setUp() {
        redisTemplate = Mockito.mock(StringRedisTemplate.class);
        redisListOperations = Mockito.mock(ListOperations.class);
        when(redisTemplate.opsForList()).thenReturn(redisListOperations);

        redisMessagePublisher = new RedisMessagePublisher(redisTemplate);
    }

    @Test
    public void shouldPublishSubdomains() {
        Subdomain expectedSubdomainOne = new Subdomain("http://secureline.tools.avast.com");
        Subdomain expectedSubdomainTwo = new Subdomain("http://gf.tools.avast.com");
        Config config = new Config();
        config.setSubdomains(List.of(expectedSubdomainOne, expectedSubdomainTwo));
        config.setCookies(new ArrayList<>());

        redisMessagePublisher.publish(config);
        verify(redisListOperations).leftPushAll("subdomains", config.getSubdomainUrls());
    }

    @Test
    public void shouldPublishCookies() {
        Cookie cookieOne = new Cookie("dlp-avast", "amazon", "mmm_amz_dlp_777_ppc_m");
        Cookie cookieTwo = new Cookie("dlp-avg", "vector", "mmm_vct_dlp_779_ppc_m");

        Config config = new Config();
        config.setSubdomains(new ArrayList<>());
        config.setCookies(List.of(cookieOne, cookieTwo));

        redisMessagePublisher.publish(config);

        String expectedCookieKeyOne = "cookie:dlp-avast:amazon";
        String expectedCookieKeyTwo = "cookie:dlp-avg:vector";
        verify(redisListOperations).leftPush(expectedCookieKeyOne, cookieOne.getData());
        verify(redisListOperations).leftPush(expectedCookieKeyTwo, cookieTwo.getData());
    }

    @Test
    public void shouldPublishSubDomainsAndCookies() {
        Subdomain subdomainOne = new Subdomain("http://secureline.tools.avast.com");
        Subdomain subdomainTwo = new Subdomain("http://gf.tools.avast.com");

        Cookie cookieOne = new Cookie("dlp-avast", "amazon", "mmm_amz_dlp_777_ppc_m");
        Cookie cookieTwo = new Cookie("dlp-avg", "vector", "mmm_vct_dlp_779_ppc_m");

        Config config = new Config();
        config.setSubdomains(List.of(subdomainOne, subdomainTwo));
        config.setCookies(List.of(cookieOne, cookieTwo));

        redisMessagePublisher.publish(config);

        String expectedCookieKeyOne = "cookie:dlp-avast:amazon";
        String expectedCookieKeyTwo = "cookie:dlp-avg:vector";

        verify(redisListOperations).leftPushAll("subdomains", config.getSubdomainUrls());
        verify(redisListOperations).leftPush(expectedCookieKeyOne, cookieOne.getData());
        verify(redisListOperations).leftPush(expectedCookieKeyTwo, cookieTwo.getData());
    }
}