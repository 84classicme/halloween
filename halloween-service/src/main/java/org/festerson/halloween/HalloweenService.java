package org.festerson.halloween;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;

@SpringBootApplication
public class HalloweenService { // implements ApplicationRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${lights.topic.name}")
    private String lightsTopic;

    @Value("${sound.topic.name}")
    private String soundTopic;

    @Value("${reset.topic.name}")
    private  String resetTopic;

    public void notifyLightsAndSound(String msg){

        kafkaTemplate.send(lightsTopic, msg);
        kafkaTemplate.send(soundTopic, msg);
    }

	public static void main(String[] args) {
        SpringApplication.run(HalloweenService.class, args);
	}

    @KafkaListener(topics = "${guest.topic.name}", groupId = "${kafka.consumer-group}")
    public void listenForGuests(String message, Acknowledgment ack) {
        System.out.println("HalloweenService Received Message in group - halloween: " + message);
        notifyLightsAndSound("Showtime!");
        ack.acknowledge();
    }

}
