package org.festerson.halloween;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;

@SpringBootApplication(scanBasePackages = {"org.festerson.halloween.config"})
public class HalloweenLights { //implements ApplicationRunner {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	final GpioController gpio = GpioFactory.getInstance();
	final GpioPinDigitalOutput lights = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "lights", PinState.LOW);

	public static void main(String[] args) {
		SpringApplication.run(HalloweenLights.class, args);
	}

	@KafkaListener(topics = "${lights.topic.name}", groupId = "${kafka.consumer-group}")
	public void listen(String message, Acknowledgment ack) throws InterruptedException {
		System.out.println("HalloweenLights Received Message in group - halloween: " + message);
			manageLights();
			ack.acknowledge();
	}

	private void manageLights() throws InterruptedException {
		System.out.println("Toggling lights...");
		lights.toggle();
		System.out.println("Toggling back to original state in 30 secs...");
		Thread.sleep(30000);
		lights.toggle();
		Thread.sleep(3000);
	}
}
