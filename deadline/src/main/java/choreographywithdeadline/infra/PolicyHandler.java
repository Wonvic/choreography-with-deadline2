package choreographywithdeadline.infra;

import choreographywithdeadline.config.kafka.KafkaProcessor;
import choreographywithdeadline.domain.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@EnableScheduling
@Transactional
public class PolicyHandler {

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderCreated'"
    )
    public void wheneverOrderCreated_Schedule(
        @Payload OrderCreated orderCreated
    ) {
        OrderCreated event = orderCreated;
        System.out.println(
            "\n\n##### listener Schedule : " + orderCreated + "\n\n"
        );

        // Sample Logic //
        Deadline.schedule(event);
    }

    @Scheduled(fixedRate = 5000)
    public void checkDeadline(){
        Deadline.sendDeadlineEvents();
    }

}
//>>> Clean Arch / Inbound Adaptor
