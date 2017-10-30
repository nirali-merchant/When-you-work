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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "availability")
public class Availability {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "availID")
	private long availID;
	
	@Column (name = "date")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column (name = "startTime")
	@Temporal(TemporalType.TIME)
	private Date startTime;
	
	@Temporal(TemporalType.TIME)
	@Column (name = "endTime")
	private Date endTime;
	
	
	
	
	
	//@ManyToMany (fetch = FetchType.LAZY, mappedBy = "shifts", cascade = CascadeType.ALL)
	@ManyToMany 
    @JoinTable (name = "studentavailability" , joinColumns = 
    {@JoinColumn (name = "availID", nullable = false, updatable = false)},
    inverseJoinColumns = {@JoinColumn (name = "personid") })
	
	private Set<StudentStaff> studentStaff = new HashSet<StudentStaff>();





	public long getAvailID() {
		return availID;
	}





	public void setAvailID(long availID) {
		this.availID = availID;
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





	public Date getEndTime() {
		return endTime;
	}





	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}





	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}





	public Set<StudentStaff> getStudentStaff() {
		return studentStaff;
	}





	public void setStudentStaff(Set<StudentStaff> studentStaff) {
		this.studentStaff = studentStaff;
	}

	
	
	
}
