package demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import jakarta.jms.Topic;

@Component
public class Sender implements CommandLineRunner {

	String message = "<message><job>${id}</job></message>";

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Topic topic;

	List<String> files() {
		List<String> contents = new ArrayList<>();
		//File cwd = File(".");
		Path cwd = FileSystems.getDefault().getPath("./data");
		File[] files = cwd.toFile().listFiles();
		for (File c : files) {
			if (c.getName().endsWith(".xml")) {
				try {
					contents.add(Files.readString(Path.of(c.getPath())));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return contents;
	}

	public void run(String... arg0) throws Exception {
		int x = 0;
		int restarted = 0;

		while (true) {
			for (String c : files()) { 
				String m = c.replace("${id}", Integer.toString(x++));
				System.out.println("Sending:  " + m);
				this.jmsMessagingTemplate.convertAndSend(this.topic, m);
				Thread.sleep(3000);
			}
			System.out.println("Restarting: " + restarted++);
			Thread.sleep(3000);
		}
	}
}
