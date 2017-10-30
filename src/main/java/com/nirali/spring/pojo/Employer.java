package com.nirali.spring.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employer")
public class Employer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "employerID")
   private long employerID;
   
   @Column (name  = "companyName")
   private String companyName;
   
   @Column (name = "address")
   private String address;
   
   @Column(name ="phoneNo")
   private String phoneNo;
   
   @Column(name ="emailAddress")
   private String emailAddress;
   
   @OneToMany(mappedBy = "employer")
   private Set<Person> person = new HashSet<Person>();
   
   @Column(name = "approved")
   private boolean approved;

   //@ManyToMany
//   @JoinTable (
//	       name="category_advert_table",
//	       joinColumns = {@JoinColumn(name="categoryID", nullable = false, updatable = false)},
//	       inverseJoinColumns = {@JoinColumn(name="advertID" )}
//	    )
   /*@ManyToMany (cascade=CascadeType.ALL)
    @JoinTable (name = "employerRoles" , joinColumns = 
   {@JoinColumn (name = "employerID", nullable = false, updatable = false)},
   inverseJoinColumns = {@JoinColumn (name = "RoleID") })
  private Set<Role> role = new HashSet<Role>();*/
   
public long getEmployerID() {
	return employerID;
}

public void setEmployerID(long employerID) {
	this.employerID = employerID;
}

public String getCompanyName() {
	return companyName;
}

public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getPhoneNo() {
	return phoneNo;
}

public void setPhoneNo(String phoneNo) {
	this.phoneNo = phoneNo;
}



public Set<Person> getPerson() {
	return person;
}

public void setPerson(Set<Person> person) {
	this.person = person;
}

public boolean isApproved() {
	return approved;
}

public void setApproved(boolean approved) {
	this.approved = approved;
}

public String getEmailAddress() {
	return emailAddress;
}

public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
}











	
	
}
