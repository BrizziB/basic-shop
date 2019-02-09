package model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="users")
public class User extends BaseEntity {
	
	@Expose
	private String email;
	
	@Expose
	private String password;
	
	User(){
	}
	
	public User(String uuid) {
		super(uuid);
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
	
}
