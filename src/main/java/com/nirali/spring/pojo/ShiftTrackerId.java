package com.nirali.spring.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ShiftTrackerId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Shifts shifts;
	
	
	private StudentStaff studentStaff;
	@ManyToOne(cascade = CascadeType.ALL)
	public Shifts getShifts() {
		return shifts;
	}

	public void setShifts(Shifts shifts) {
		this.shifts = shifts;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public StudentStaff getStudentStaff() {
		return studentStaff;
	}

	public void setStudentStaff(StudentStaff studentStaff) {
		this.studentStaff = studentStaff;
	}

	
	
	
	
	
}
