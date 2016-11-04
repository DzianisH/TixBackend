package org.tix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tix.domain.Avatar;

import java.util.List;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long>{
	Avatar findByLogin(String login);
	List<Avatar> findAllByUserId(Integer userId);
}
