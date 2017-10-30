package com.nirali.spring.pojo;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "shifts")
public class Shifts {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "shiftID")
	private long shiftID;
	@Column (name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column (name = "startTime")
	@Temporal(TemporalType.TIME)
	private Date startTime;
	@Temporal(TemporalType.TIME)
	@Column (name = "endTime")
	private Date endTime;
	
	@ManyToOne
	@JoinColumn(name = "personID")
	private SupervisorStaff supervisorStaff;
	
	@Column(name = "employeeCount")
	private int employeeCount;
	
	
	
	//@ManyToMany (cascade=CascadeType.ALL)
  //  @JoinTable (name = "shiftTracker" , joinColumns = 
  //  {@JoinColumn (name = "shiftID", nullable = false, updatable = false)},
  //  inverseJoinColumns = {@JoinColumn (name = "personid") })
	//private Set<StudentStaff> studentStaff = new HashSet<StudentStaff>();
	
	@OneToMany(mappedBy = "primaryKey.shifts", cascade = CascadeType.ALL)
	private Set<ShiftTracker> shiftTracker  = new HashSet<ShiftTracker>();
	
	
	@OneToMany(mappedBy = "shifts")
	private Set<OpenShifts> openShifts;

	public long getShiftID() {
		return shiftID;
	}

	public void setShiftID(long shiftID) {
		this.shiftID = shiftID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public SupervisorStaff getSupervisorStaff() {
		return supervisorStaff;
	}

	public void setSupervisorStaff(SupervisorStaff supervisorStaff) {
		this.supervisorStaff = supervisorStaff;
	}

	public Set<ShiftTracker> getShiftTracker() {
		return shiftTracker;
	}

	public void setShiftTracker(Set<ShiftTracker> shiftTracker) {
		this.shiftTracker = shiftTracker;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}

	public Set<OpenShifts> getOpenShifts() {
		return openShifts;
	}

	public void setOpenShifts(Set<OpenShifts> openShifts) {
		this.openShifts = openShifts;
	}
	
	
	

	

	/*public Set<StudentStaff> getStudentStaff() {
		return studentStaff;
	}

	public void setStudentStaff(Set<StudentStaff> studentStaff) {
		this.studentStaff = studentStaff;
	}*/
    
	
	
	
}
