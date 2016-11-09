package org.tix.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;

import static org.tix.utils.ObjectUtils.same;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@Entity
public class Avatar implements Serializable{
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;
	@Column(nullable = false)
	private Integer color;
	@Column(nullable = false, unique = true, length = 31)
	private String login;
	// owner of particular avatar
	@JsonIgnore
	@ManyToOne
	@JoinColumn
	private User user;

	public Avatar(){}
	public Avatar(Integer color, String login, User user){
		this.color = color;
		this.login = login;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public Avatar setId(Long id) {
		this.id = id;
		return this;
	}

	public Integer getColor() {
		return color;
	}

	public Avatar setColor(Integer color) {
		this.color = color;
		return this;
	}

	public String getLogin() {
		return login;
	}

	public Avatar setLogin(String login) {
		this.login = login;
		return this;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString(){
		return "{id: " + id +
				", user_id: " + user.getId() +
				", login: '" + login + "' " +
				", color: " + color + "}";
	}

	@Override
	public int hashCode(){
		return (int)(long)id;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Avatar && equals((Avatar) obj);
	}

	public boolean equals(Avatar avatar){
		return avatar == this || same(id, avatar.id)
				&& same(login, avatar.login)
				&& same(color, avatar.color)
				&& same(user, avatar.user);
	}

}
