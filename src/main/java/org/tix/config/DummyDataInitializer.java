package org.tix.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tix.domain.User;
import org.tix.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
@Component
public class DummyDataInitializer {
	private static final Logger LOG = Logger.getLogger(DummyDataInitializer.class);

	private final UserRepository userRepository;

	@Inject
	public DummyDataInitializer(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Value("${spring.datasource.url}")
	private String databaseUri;

	@PostConstruct
	public void initDummyBeans(){
		LOG.info("Injecting dummy data to " + databaseUri);

		List<User> dummyUsers = userRepository.save(Arrays.asList(
				new User(0xff0000, "r00t"),
				new User(0x00ff00, "karamber"),
				new User(0x0000ff, "godfather"),
				new User(0x0ff000, "samurai"),
				new User(0x000ff0, "belka99"),
				new User(0xf0000f, "broadcaster")
		));

		if(LOG.isDebugEnabled()){
			StringBuilder sb = new StringBuilder();
			sb.append("New users: ");
			dummyUsers.forEach(user -> sb.append(user).append("; "));
			LOG.debug(sb.toString());
		}
	}
}
