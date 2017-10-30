package com.nirali.spring.pojo;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "shiftTracker")
@AssociationOverrides({
    @AssociationOverride(name = "primaryKey.studentStaff",
        joinColumns = @JoinColumn(name = "personID")),
    @AssociationOverride(name = "primaryKey.shifts",
        joinColumns = @JoinColumn(name = "shiftID")) })
public class ShiftTracker {

	
	private ShiftTrackerId primaryKey = new ShiftTrackerId();
	
	@Column(name = "acknowledged")
	private boolean acknowledged;
	@Column(name = "dropped")
	private boolean dropped;
	@Column(name = "open")
	private boolean open;
	@Column(name = "swapped")
	private boolean swapped;
	@Column(name = "swappedWithStudentID")
	private long swappedWithStudentID;
	@Column(name="swappedShiftID")
	private long swappedShiftID;
	
	
	
	
	@EmbeddedId
	public ShiftTrackerId getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(ShiftTrackerId primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Transient
	public StudentStaff getStudentStaff() {
        return getPrimaryKey().getStudentStaff();
    }
 
    public void setStudentStaff(StudentStaff studentStaff) {
        getPrimaryKey().setStudentStaff(studentStaff);
    }
 
    @Transient
    public Shifts getShifts() {
        return getPrimaryKey().getShifts();
    }
 
    public void setShifts(Shifts shifts) {
        getPrimaryKey().setShifts(shifts);
    }

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public boolean isDropped() {
		return dropped;
	}

	public void setDropped(boolean dropped) {
		this.dropped = dropped;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isSwapped() {
		return swapped;
	}

	public void setSwapped(boolean swapped) {
		this.swapped = swapped;
	}

	public long getSwappedWithStudentID() {
		return swappedWithStudentID;
	}

	public void setSwappedWithStudentID(long swappedWithStudentID) {
		this.swappedWithStudentID = swappedWithStudentID;
	}

	public long getSwappedShiftID() {
		return swappedShiftID;
	}

	public void setSwappedShiftID(long swappedShiftID) {
		this.swappedShiftID = swappedShiftID;
	}
 
	
    
    
	
	
}
