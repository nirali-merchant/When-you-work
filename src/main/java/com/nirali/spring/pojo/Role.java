package com.nirali.spring.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "role")
public class Role {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "roleID")
	private long roleID;
	
	@Column (name = "roleName")
	private String roleName;
	
	/*@ManyToMany (fetch = FetchType.LAZY, mappedBy = "role")
	private Set<Employer> employer = new HashSet<Employer>();*/
	
	@OneToMany (mappedBy = "role")
	private Set<Person> person = new HashSet<Person>();

	

	public long getRoleID() {
		return roleID;
	}

	public void setRoleID(long roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	

	public Set<Person> getPerson() {
		return person;
	}

	public void setPerson(Set<Person> person) {
		this.person = person;
	}
	
	

}
