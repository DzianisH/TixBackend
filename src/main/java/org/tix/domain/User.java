package org.tix.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import static org.tix.utils.ObjectUtils.same;

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

	@Transient
	private Avatar activeAvatar;

	// can't deal with serialisation issue ;c
//	private List<Avatar> avatarList;


	public User(){}
	public User(String email, String password){
		this.email = email;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public User setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
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
		return this == user || same(id, user.id)
				&& same(email, user.email)
				&& same(password, user.password);
	}

	public Avatar getActiveAvatar() {
		return activeAvatar;
	}

	public User setActiveAvatar(Avatar activeAvatar) {
		this.activeAvatar = activeAvatar;
		return this;
	}
}
