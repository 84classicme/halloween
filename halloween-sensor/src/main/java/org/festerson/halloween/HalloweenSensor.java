package org.festerson.halloween;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class HalloweenSensor  {

	private KafkaTemplate<String, String> kafkaTemplate;
	private boolean active = false;


    public void sendMessage(String msg){
        KafkaTemplate<String, String> kafkaTemplate = kafkaTemplate();
		kafkaTemplate.send("service", msg);
	}

    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "arthur:9092,the-tick:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }


    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


	public static void main(String[] args) {
        HalloweenSensor sensor = new HalloweenSensor();
        sensor.run();
	}

	public void run() {

        System.out.println("Starting HalloweenSensor...");

        final GpioController gpioSensor = GpioFactory.getInstance();
        final GpioPinDigitalInput sensor = gpioSensor.provisionDigitalInputPin(RaspiPin.GPIO_04, PinPullResistance.PULL_DOWN);
        sensor.setDebounce(1000,PinState.HIGH);

        // create and register gpio pin listener
        sensor.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                handleEvent(event);
            }
        });

        try {
            // keep program running until user aborts
            for (;;) {
                Thread.sleep(500);
            }
        }
        catch (final Exception e) {
            System.out.println(e.getMessage());
        }

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        //gpio.shutdown();  // <--- implement this method call if you wish to terminate the Pi4J GPIO controller

	}

	protected void handleEvent(GpioPinDigitalStateChangeEvent event){
        if(event.getState().isHigh() && !active) {
            active = true;
            System.out.println("Guest: " + Date.from(Instant.now()).toString());
            sendMessage("Trick or Treat!");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            active = false;
        }
    }
}
