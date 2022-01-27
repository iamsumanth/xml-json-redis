package learn.service;

import learn.redis.publisher.RedisMessagePublisher;
import learn.xml.XMLParser;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;

class XmlExporterTest {

    @Test
    public void readXMLFileAndPublishJSONToRedis() throws IOException {
        XMLParser xmlParser = Mockito.mock(XMLParser.class);
        FileReader fileReader = Mockito.mock(FileReader.class);
        RedisMessagePublisher redisMessagePublisher = Mockito.mock(RedisMessagePublisher.class);
        XmlExporter xmlExporter = new XmlExporter(fileReader, xmlParser, redisMessagePublisher);

        xmlExporter.exportXmlFromPathToRedis("/src/test-path");
        Mockito.verify(redisMessagePublisher).publish(ArgumentMatchers.any());

    }

}