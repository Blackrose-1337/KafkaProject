package blackrose.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class ProducerApplication {

    private static final Logger LOG = Logger.getLogger(ProducerApplication.class);

    @Value("${company.kafka.topic}")
    private String TOPIC;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public NewTopic topic() {
        LOG.infof("Creating/Connect to topic: %s{}", TOPIC);
        return TopicBuilder.name(TOPIC)
                .partitions(10)
                .replicas(1)
                .build();
    }
}
