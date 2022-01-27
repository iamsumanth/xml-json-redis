package learn.xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import learn.model.Config;
import org.springframework.stereotype.Service;

@Service
public class XMLParser {

    public Config read(String xmlData) throws JsonProcessingException {
        ObjectMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xmlData, Config.class);
    }
}
