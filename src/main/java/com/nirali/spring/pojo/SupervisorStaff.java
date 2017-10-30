package com.nirali.spring.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "supervisorstaff")
@PrimaryKeyJoinColumn(name="personid")  
public class SupervisorStaff  extends Person{
	
	@OneToMany(mappedBy = "supervisorStaff")
	Set<Shifts> shifts ;

	public Set<Shifts> getShifts() {
		return shifts;
	}

	public void setShifts(Set<Shifts> shifts) {
		this.shifts = shifts;
	}
	
	
	
	
	
}
	
	