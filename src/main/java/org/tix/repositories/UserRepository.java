package org.tix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tix.domain.User;

import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String login);
}
