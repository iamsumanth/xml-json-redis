package learn.model;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Config {
    private List<Subdomain> subdomains;
    private List<Cookie> cookies;

    public List<String> getSubdomainUrls() {
        return subdomains.stream().map(Subdomain::getUrl).collect(Collectors.toList());
    }
}
