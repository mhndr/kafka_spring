package com.example.kafka.resource;


import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import java.util.List;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.model.Location;
import com.example.kafka.peristance.JPAUtility;
import com.example.kafka.repository.LocationRepository;

@RestController
@RequestMapping("/kafka")
public class LocationResource {
	private static final String TOPIC="Kafka_Example";
	Properties producerProperties = new Properties();
	@Value("${kafka.bootstrap.servers}")
	private String kafkaBootstrapServers;
	//@Autowired
	//private KafkaTemplate<String,Location> kafkaTemplate;
	KafkaProducer<String, Location> producer; 
	@Autowired
	LocationRepository locationRepository;


	 
	 @GetMapping("/all")
	 public List<Location> getAll() {
		 return locationRepository.findAll();
	 }

	
	
	 @GetMapping("/publish/{message}")
	 public String post(@PathVariable("message") final String message) throws Exception {
		String list[] = message.split(" ");
		long time = System.currentTimeMillis();
		
		System.out.println(message);
		if(list.length != 3) {
			return "Failed to Publish - Incorrect Data given";
		}
		//kafkaTemplate.send(TOPIC, new Location(list[0],list[1],list[2]));
		
		producerProperties.put("key.serializer", org.apache.kafka.common.serialization.StringSerializer.class.getName());
		producerProperties.put("value.serializer", org.springframework.kafka.support.serializer.JsonSerializer.class.getName());
		producerProperties.put("bootstrap.servers", kafkaBootstrapServers);
		producer = new KafkaProducer<>(producerProperties);
		
		Location new_location = new Location(list[0],list[1],list[2]);
		
		try {
			//publish to kafka
			final ProducerRecord <String,Location> record = new ProducerRecord<>(TOPIC, new_location );
			 RecordMetadata metadata = producer.send(record).get();
             long elapsedTime = System.currentTimeMillis() - time;
             System.out.printf("sent record(key=%s value=%s) " +
                             "meta(partition=%d, offset=%d) time=%d\n",
                     record.key(), record.value(), metadata.partition(),
                     metadata.offset(), elapsedTime);
	 	}
		finally {
			producer.flush();
			producer.close();
		}
		
		//write to database
		EntityManager em = JPAUtility.getEntityManager();	
		
		em.getTransaction().begin();
		em.persist(new_location);
		em.getTransaction().commit();
		
	
	
		return "Published Successfully";
	}	

}
