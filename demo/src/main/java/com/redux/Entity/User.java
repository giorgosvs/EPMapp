package com.redux.Entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	
	@Column(name = "first_name")
	@Size(min = 3, max = 50)
	@NotNull
	private String firstName;

	@Column(name = "last_name")
	@Size(min = 3, max = 50)
	@NotNull
	private String lastName;

	@Column(name = "email")
	@Email(message = "Please enter a valid email address")
	@NotNull
	private String email;
	
	@Column(name = "username")
	@NotNull
	@Size(min = 3, max = 50)
	private String username;

	@Column(name = "password")
	@NotNull
	private String password;
	private String matchingPassword;

	@Column(name = "date_of_birth")
	@NotNull
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private String date;

	@Column(name = "phone_number")
	@NotNull
	private String phoneNumber;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "auth_id"))
	private Collection<Role> roles;


    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
	private List<Application> applications;

	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name="created_time",updatable= false)
	private Date createdTime;
	
	@Column(name ="verification_code",updatable= false)
	private String verificationCode;

	public User() {

	}

	public User(String firstName, String lastName, String email, String username, String password, String date,
			String phoneNumber, Collection<Role> roles, List<Application> applications, boolean enabled) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.date = date;
		this.phoneNumber = phoneNumber;
		this.roles = roles;
		this.applications = applications;
		this.enabled = enabled;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getPassword() {
		return password;
	}
	
	public String setMatchingPassword() {
		return matchingPassword;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	
	
	
	

}
