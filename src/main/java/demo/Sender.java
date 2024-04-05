package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import jakarta.jms.Queue;

@Component
public class Sender implements CommandLineRunner {

	String message = "<message><job>${id}</job></message>";
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;

	public void run(String... arg0) throws Exception {
		int x = 0;
		while (true) {
			String m = message.replace("${id}", Integer.toString(x++));
			System.out.println("Sending:  " + m );
			this.jmsMessagingTemplate.convertAndSend(this.queue, m);
			Thread.sleep(3000);
		}
	}
}
