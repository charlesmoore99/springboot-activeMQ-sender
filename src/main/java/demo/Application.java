package demo;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;


@SpringBootApplication
@EnableJms
public class Application {
	
	public static void main(String[] args) {
		System.out.println("starting!");
		SpringApplication.run(Application.class, args);
		System.out.println("done!");
	}

    @Bean
    public  ActiveMQQueue queue() {
        return new ActiveMQQueue("theQueue");
    }
}
