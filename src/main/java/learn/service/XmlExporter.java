package learn.service;

import learn.model.Config;
import learn.redis.publisher.RedisMessagePublisher;
import learn.xml.XMLParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class XmlExporter {

    private final FileReader fileReader;
    private final XMLParser xmlParser;
    private final RedisMessagePublisher redisMessagePublisher;

    @Autowired
    public XmlExporter(FileReader fileReader, XMLParser xmlParser, RedisMessagePublisher redisMessagePublisher) {
        this.fileReader = fileReader;
        this.xmlParser = xmlParser;
        this.redisMessagePublisher = redisMessagePublisher;
    }

    public void exportXmlFromPathToRedis(String filePath) throws IOException {
        String xmlContent = fileReader.read(filePath);
        Config config = xmlParser.read(xmlContent);
        redisMessagePublisher.publish(config);
    }
}
