package com.example.tasklist.domain.user;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.example.tasklist.domain.task.Task;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "users")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String username;
	private String password;
	
	@Transient
	private String passwordConfirmation;
	

	@Column(name = "role")
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "users_roles")
	@Enumerated(value = EnumType.STRING)
	private Set<Role> roles;
	
	
	@CollectionTable(name = "users_tasks")
	@OneToMany
	@JoinColumn(name = "task_id")
	private List<Task> tasks;
	
	public User() {}
	
	public User(Long id, String name, String username, String password, String passwordConfirmation, Set<Role> roles,
			List<Task> tasks) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.roles = roles;
		this.tasks = tasks;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRolse(Set<Role> roles) {
		this.roles = roles;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles + "]";
	}

	
}
