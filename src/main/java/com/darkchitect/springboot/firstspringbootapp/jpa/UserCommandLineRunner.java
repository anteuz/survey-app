package com.darkchitect.springboot.firstspringbootapp.jpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory
			.getLogger(UserCommandLineRunner.class);

	private final static String ROLEADMIN = "Admin";
	private final static String ROLEUSER = "User";
	
	@Autowired
	private UserRepository repository;

	@Override
	public void run(String... args) throws Exception {

		repository.save(new User("Ranga", ROLEADMIN));
		repository.save(new User("Ravi", ROLEUSER));
		repository.save(new User("Satish", ROLEADMIN));
		repository.save(new User("Raghu", ROLEUSER));

		for (User user : repository.findAll()) {
			if(log.isInfoEnabled())
				log.info(user.toString());
		}

		if(log.isInfoEnabled()) {
			log.info("Admin users are.....");
			log.info("____________________");
		}
		for (User user : repository.findByRole(ROLEADMIN)) {
			if(log.isInfoEnabled())
				log.info(user.toString());
		}

	}
}