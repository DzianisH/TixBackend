package org.tix.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tix.domain.Avatar;
import org.tix.domain.User;
import org.tix.repositories.AvatarRepository;
import org.tix.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 03.11.2016.
 */
@Component
public class DummyDataInitializer {
	private static final Logger LOG = Logger.getLogger(DummyDataInitializer.class);

	private final AvatarRepository avatarRepository;
	private final UserRepository userRepository;

	@Inject
	public DummyDataInitializer(AvatarRepository avatarRepository, UserRepository userRepository) {
		this.avatarRepository = avatarRepository;
		this.userRepository = userRepository;
	}

	@Value("${spring.datasource.url}")
	private String databaseUri;

	@PostConstruct
	public void initDummyBeans(){
		LOG.info("Injecting dummy data to " + databaseUri);

		avatarRepository.deleteAll();
		userRepository.deleteAll();

		List<User> dummyUsers = userRepository.save(Arrays.asList(
				new User("gavrilovetsden@gmail.com", "1111"),
				new User("lonlycoder@tut.by", "2222")
		));

		List<Avatar> dummyAvatars = avatarRepository.save(Arrays.asList(
				new Avatar(0xff0000, "r00t", dummyUsers.get(0)),
				new Avatar(0x00ff00, "karamber", dummyUsers.get(0)),
				new Avatar(0x0000ff, "godfather", dummyUsers.get(0)),
				new Avatar(0x0ff000, "samurai", dummyUsers.get(1)),
				new Avatar(0x000ff0, "belka99", dummyUsers.get(1)),
				new Avatar(0xf0000f, "broadcaster", dummyUsers.get(1))
		));

		if(LOG.isDebugEnabled()){
			LogBuilder builder = new LogBuilder("Initialise dummy entities:");
			builder.append("\nNew users: ")
					.appendAll(dummyUsers)
					.append("\nNew avatars: ")
					.appendAll(dummyAvatars)
					.logDebug();
		}
	}

	private static class LogBuilder{
		private final StringBuilder sb = new StringBuilder();

		LogBuilder(String prefix){
			sb.append(prefix);
		}

		LogBuilder append(String chunk){
			sb.append(chunk);
			return this;
		}

		LogBuilder appendAll(Collection<?> chunks){
			return appendAll(chunks, "; ");
		}

		LogBuilder appendAll(Collection<?> chunks, String separator){
			chunks.forEach(chunk -> sb.append(chunk).append(separator));
			return this;
		}

		void logDebug(){
			LOG.debug(sb.toString());
		}

	}
}
