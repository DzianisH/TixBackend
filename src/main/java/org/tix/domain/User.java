package org.tix.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Dzianis_Haurylavets on 04.11.2016.
 */
@Entity
public class User {
	@Id @GeneratedValue
	@Column(nullable = false)
	private Integer id;
	@Column(unique = true, length = 31, nullable = false)
	private String email;
	@Column(length = 31, nullable = false)
	private String password;

	public User(){}
	public User(String email, String password){
		this.email = email;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode(){
		return id;
	}

	@Override
	public String toString(){
		return "{id: " + id + ", "
				+ "email: '" + email + "', "
				+ "password: '" + password + "'}";
	}

	@Override
	public boolean equals(Object obj){
		return obj instanceof User && equals((User) obj);
	}

	public boolean equals(User user){
		return this == user || id != null && id.equals(user.id);
	}
}
