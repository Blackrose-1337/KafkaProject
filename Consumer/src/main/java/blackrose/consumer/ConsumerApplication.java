package blackrose.consumer;

import blackrose.consumer.dto.AdUserDto;
import blackrose.consumer.service.PowershellService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class ConsumerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerApplication.class);

    private final PowershellService powershellService;

    @Autowired
    public ConsumerApplication(PowershellService powershellService) {
        this.powershellService = powershellService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        LOG.info("SP-Consumer started at {}", System.currentTimeMillis());
    }

    @KafkaListener(id = "ADServer", topics = "topic1", properties = {
            "bootstrap.servers=10.0.2.6:9092",
    })
    public void listen(String in) {
        try {
            LOG.info("SP-Consumer with ID myId Received: {}", in);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            System.out.println("-------------------------------------------Daten----------------------------------------------------");
            System.out.println(in);
            System.out.println("-----------------------------------------------------------------------------------------------");
            AdUserDto adUserDto = objectMapper.readValue(in, AdUserDto.class);
            String command = powershellService.createAdUserCommand(adUserDto);
//            String command = "get-aduser -Filter *";
            System.out.println("-----------------------------------------------------------------------------------------------");
            String output = powershellService.executeCommand(command);
            LOG.info("SP-Consumer with ID myId Output: {}", output);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
