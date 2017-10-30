package com.nirali.spring.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "studentstaff")
@PrimaryKeyJoinColumn(name="personid")  
public class StudentStaff  extends Person{
	
	 public StudentStaff()
	 {
		
	 }
	
	//@ManyToMany (fetch = FetchType.LAZY, mappedBy = "studentStaff", cascade = CascadeType.ALL)
	//private Set<Shifts> shifts;
	 @OneToMany(mappedBy = "primaryKey.studentStaff", cascade = CascadeType.ALL)
	 private Set<ShiftTracker> shiftTracker  = new HashSet<ShiftTracker>();
	
	@ManyToMany (fetch = FetchType.LAZY, mappedBy = "studentStaff")
	private Set<Availability> availabilites;

	public Set<ShiftTracker> getShiftTracker() {
		return shiftTracker;
	}

	public void setShiftTracker(Set<ShiftTracker> shiftTracker) {
		this.shiftTracker = shiftTracker;
	}

	public Set<Availability> getAvailabilites() {
		return availabilites;
	}

	public void setAvailabilites(Set<Availability> availabilites) {
		this.availabilites = availabilites;
	}
	
	
	
	
	}
	
	
	
	



