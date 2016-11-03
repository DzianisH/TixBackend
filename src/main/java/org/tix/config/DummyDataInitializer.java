package org.tix.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tix.domain.Avatar;
import org.tix.repositories.AvatarRepository;

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

	private final AvatarRepository avatarRepository;

	@Inject
	public DummyDataInitializer(AvatarRepository avatarRepository) {
		this.avatarRepository = avatarRepository;
	}

	@Value("${spring.datasource.url}")
	private String databaseUri;

	@PostConstruct
	public void initDummyBeans(){
		LOG.info("Injecting dummy data to " + databaseUri);

		List<Avatar> dummyAvatars = avatarRepository.save(Arrays.asList(
				new Avatar(0xff0000, "r00t"),
				new Avatar(0x00ff00, "karamber"),
				new Avatar(0x0000ff, "godfather"),
				new Avatar(0x0ff000, "samurai"),
				new Avatar(0x000ff0, "belka99"),
				new Avatar(0xf0000f, "broadcaster")
		));

		if(LOG.isDebugEnabled()){
			StringBuilder sb = new StringBuilder();
			sb.append("New avatars: ");
			dummyAvatars.forEach(avatar -> sb.append(avatar).append("; "));
			LOG.debug(sb.toString());
		}
	}
}
