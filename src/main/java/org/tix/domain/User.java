package org.tix.domain;

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
	private Integer id;
	private Integer color;
	private String login;

	public User(){}
	public User(Integer id){
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString(){
		return new StringBuilder().append("User:{id: ").append(id)
				.append(", login:").append(login)
				.append(", color: ").append(color).append("}")
				.toString();
	}

	@Override
	public int hashCode(){
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof User && equals((User) obj);
	}

	public boolean equals(User user){
		return id.equals(user.id);
	}
}
