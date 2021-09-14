package org.festerson.halloween;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class HalloweenAlert {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "arthur:9092,the-tick:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        System.out.println("Sending guest alert...");
        Producer<Integer, String> producer = new KafkaProducer<Integer, String>(props);
        ProducerRecord producerRecord = new ProducerRecord<String, String>("guest", "detector", "Trick or Treat");
        producer.send(producerRecord);
        producer.close();
        System.out.println("done.");
    }
}
