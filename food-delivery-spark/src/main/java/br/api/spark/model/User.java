package br.api.spark.model;

public class User {

	private int id;
	
	private String email;
	
	private String password;
	
	private String name;
	
	private String lastName;
	
	private int active;
	
	private int roleId;
	
	public User(String name, String lastName, String email, String password, int active, int roleId) {
		this.active = active;
		this.email = email;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	
	
	
}
