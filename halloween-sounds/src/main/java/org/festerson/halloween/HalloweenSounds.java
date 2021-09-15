package org.festerson.halloween;

import javax.sound.sampled.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;

@SpringBootApplication(scanBasePackages = {"org.festerson.halloween.config"})
public class HalloweenSounds {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ResourceLoader resourceLoader;

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static void main(String[] args) {
        SpringApplication.run(HalloweenSounds.class, args);
    }

    @KafkaListener(topics = "${sound.topic.name}", groupId = "${kafka.consumer-group}")
    public void listen(String message, Acknowledgment ack) throws InterruptedException {
        System.out.println("HalloweenSounds received message: " + message);
        manageSound();
        ack.acknowledge();
    }

    private void manageSound()  {
        System.out.println("Playing sound on OS " + OS + "...");
        try {
            if ( OS.indexOf("win") >= 0 ) {
                Resource resource = resourceLoader.getResource("classpath:/halloween.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource.getInputStream());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } else {
                Process p=Runtime.getRuntime().exec("paplay -p --device=2 /home/pi/halloween/halloween.wav");
                p.waitFor();
            }
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex.getMessage());
            ex.printStackTrace(System.err);
        }
        System.out.println("done.");
    }
}
