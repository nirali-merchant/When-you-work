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
@Table (name = "openshifts")
public class OpenShifts {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "openshiftID")
	private long openshiftID;
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
	@JoinColumn(name= "shiftID")
	private Shifts shifts;

	public long getOpenshiftID() {
		return openshiftID;
	}

	public void setOpenshiftID(long openshiftID) {
		this.openshiftID = openshiftID;
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

	public Shifts getShifts() {
		return shifts;
	}

	public void setShifts(Shifts shifts) {
		this.shifts = shifts;
	}
	
	
	
	
	
}