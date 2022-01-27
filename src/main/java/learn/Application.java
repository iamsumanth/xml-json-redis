package learn;

import learn.service.XmlExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws InterruptedException, IOException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        XmlExporter xmlExporter = ctx.getBean(XmlExporter.class);

        String configXmlPath = System.getenv("CONFIG_XML_PATH");
        System.out.println("Path of config file");
        System.out.println(configXmlPath);
        xmlExporter.exportXmlFromPathToRedis(configXmlPath);
        Thread.sleep(5000L);
    }
}
