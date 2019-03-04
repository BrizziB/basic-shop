package model;

import java.util.List;

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
	
	@Expose
	private String firstname;
	
	@Expose
	private String secondname;
	
	@Expose
	private String age;
	
	@Expose
	private String country;
	
	@Expose
	private String city;
	
	@Expose
	private String address;
	
	@Expose
	private String mainHobby;
	
	@Expose
	private String job;
	
	@Expose
	private String favTvShow;
	


	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMainHobby() {
		return mainHobby;
	}

	public void setMainHobby(String mainHobby) {
		this.mainHobby = mainHobby;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getFavTvShow() {
		return favTvShow;
	}

	public void setFavTvShow(String favTvShow) {
		this.favTvShow = favTvShow;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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
	
	public void mergeUser(User user) {
		country = user.country;
		address = user.address;
		city = user.city;
		address = user.address;
		age = user.age;
		firstname = user.firstname;
		secondname = user.secondname;
		mainHobby = user.mainHobby;
		job = user.job;
		favTvShow = user.favTvShow;
	}
	
}
