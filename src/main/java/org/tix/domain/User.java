package org.tix.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Dzianis_Haurylavets on 27.10.2016.
 */
@Entity
public class User {
	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;
	@Column(nullable = false)
	private Integer color;
	@Column(nullable = false, unique = true, length = 31)
	private String login;

	public User(){}
	public User(Integer color, String login){
		this.color = color;
		this.login = login;
	}

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public Integer getColor() {
		return color;
	}

	public User setColor(Integer color) {
		this.color = color;
		return this;
	}

	public String getLogin() {
		return login;
	}

	public User setLogin(String login) {
		this.login = login;
		return this;
	}

	@Override
	public String toString(){
		return "User:{id: " + id +
				", login:" + login +
				", color: " + color + "}";
	}

	@Override
	public int hashCode(){
		return (int)(long)id;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof User && equals((User) obj);
	}

	public boolean equals(User user){
		return id.equals(user.id);
	}
}
