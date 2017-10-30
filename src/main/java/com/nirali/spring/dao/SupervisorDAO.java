package com.nirali.spring.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.nirali.spring.pojo.Availability;
import com.nirali.spring.pojo.OpenShifts;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;

public class SupervisorDAO extends DAO {

	public SupervisorStaff registerSupervisor(SupervisorStaff s) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");

			SupervisorStaff ss = new SupervisorStaff();
			ss.setFirstName(s.getFirstName());
			ss.setLastName(s.getLastName());
			ss.setEmailID(s.getEmailID());
			ss.setPassword(s.getPassword());
			ss.setRole(s.getRole());
			ss.setEmployer(s.getEmployer());
			ss.setFilename(s.getFilename());
			getSession().save(ss);
			commit();

			return ss;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Exception while creating Student:" + e);
		}
	}

	public List<Shifts> assignShifts(Date date, Date startTime, Date endTime) {
		// select * from shifts where date = date and time>=startTime and
		// time<=endTime
		//
		Query q = getSession()
				.createQuery("from shifts where date = :date and time >= :startTime and " + "time <= :endTime ");
		List<Shifts> shiftsAvailabile = q.list();

		return shiftsAvailabile;

		// return null;

	}

	public List<Availability> getStudents(Date date, Date startTime, Date endTime) {
		Query q = getSession().createQuery(
				"from Availability where date = :date and " + "startTime <= :startTime and endTime >= :endTime");
		q.setDate("date", date);
		q.setTime("startTime", startTime);
		q.setTime("endTime", endTime);
		System.out.println("sd=" + startTime);
		System.out.println("ed=" + endTime);
		List<Availability> availabilityList = q.list();
		System.out.println("size=" + availabilityList.size());
		return availabilityList;
	}

	public Set<StudentStaff> getThisEmployerStudents(Set<StudentStaff> availableStuds, Long eid) {
		System.out.println("inside getThisEmployerStudents method");
		System.out.println(availableStuds.size());
		Set<StudentStaff> ss = new HashSet<StudentStaff>();
		for (StudentStaff s : availableStuds) {
			Long sid = s.getPersonID();
			System.out.println("sid=" + sid);
			begin();
			Query q = getSession().createQuery("from StudentStaff where personID = :sid");
			q.setLong("sid", sid);
			StudentStaff result = (StudentStaff) q.uniqueResult();
			System.out.println("result=" + result.getFirstName());
			commit();
			System.out.println("student employer id=" + result.getEmployer().getEmployerID());
			System.out.println("eid=" + eid);
			if (result.getEmployer().getEmployerID() == eid) {
				System.out.println("inside dao if=" + result.getFirstName() + "this person matched");
				ss.add(result);
			}
		}
		return ss;
	}

	public StudentStaff getStudent(long id) {
		Query q = getSession().createQuery("from StudentStaff where personID = :id");
		q.setLong("id", id);
		StudentStaff s = (StudentStaff) q.uniqueResult();
		return s;

	}

	public void assignEachStudentShift(ShiftTracker st) {
		// select * from shifts where date = date and time>=startTime and
		// time<=endTime
		//
		begin();
		getSession().save(st);
		commit();

		// return null;

	}

	public void assignEachOpenShift(OpenShifts os) {
		// select * from shifts where date = date and time>=startTime and
		// time<=endTime
		//
		begin();
		getSession().save(os);
		commit();

		// return null;

	}

	public void assignStudentShifts(Shifts shifts) {
		begin();
		getSession().save(shifts);
		commit();

	}

	public void deleteShift(Shifts shift) {
		begin();
		getSession().delete(shift);
		System.out.println(" X");
		commit();
	}

	/*
	 * public void assignShifts(Shifts selectedShift) { //select * from shifts
	 * where date = date and time>=startTime and time<=endTime // begin();
	 * getSession().save(selectedShift); commit(); for(StudentStaff s :
	 * selectedShift.getStudentStaff()) {
	 * 
	 * } selectedShift.setAssignedInd(assignedInd); Query q = getSession().
	 * createQuery("from shifts where date = :date and time >= :startTime and "
	 * + "time <= :endTime "); List<Shifts> shiftsAvailabile = q.list();
	 * 
	 * return shiftsAvailabile;
	 * 
	 * //return null;
	 * 
	 * }
	 */

	public List<Shifts> getShift(Date date, Date startTime, Date endTime) {
		begin();
		Query q = getSession()
				.createQuery("from Shifts where date = :date and startTime = :startTime and endTime =:endTime");
		q.setDate("date", date);
		q.setTime("startTime", startTime);
		q.setTime("endTime", endTime);
		System.out.println("start=" + startTime);
		System.out.println("edn=" + endTime);
		List<Shifts> shift = q.list();

		// System.out.println("shiftid&&&&"+shift.getShiftID());
		commit();
		return shift;

	}

	public List<ShiftTracker> getTodaysEmployees(Long employerid) {
		DateFormat dateFormat = new SimpleDateFormat();
		Date date = new Date();

		begin();
		// Query q = getSession().createQuery("from ShiftTracker where
		// shifts.date = :date and shifts.person.employerID =:employerid and
		// shifts.acknowledged = :acknowledged");
		// Query q = getSession().createQuery("select st.personID from
		// ShiftTracker st inner join Shifts s inner join StudentStaff ss where
		// st.ShiftID = s.shiftID AND st.date = :date and st.acknowledged =
		// :acknowledged");

		// Query q = getSession().createQuery("from ShiftTracker as st inner
		// join st.shifts as s with s.date = :date");
		// Query q = getSession().createQuery("from Shifts s, ShiftTracker st
		// where st.shiftID = s.shiftID");

		// select * from shifttracker st where st.shiftID in (select shiftID
		// from shift s where s.date =:date")

		Query q = getSession().createQuery(
				"from ShiftTracker as st where st.acknowledged = :acknowledged and  st.shiftID in (select s.shiftID from Shifts as s where s.date = :date ");

		q.setDate("date", date);
		// q.setLong("employerid", employerid);
		q.setBoolean("acknowledged", true);
		List<ShiftTracker> shifts = q.list();
		commit();
		return shifts;

	}

	public SupervisorStaff getSupervisor(long id) {
		Query q = getSession().createQuery("from SupervisorStaff where personID = :id");
		q.setLong("id", id);
		SupervisorStaff s = (SupervisorStaff) q.uniqueResult();
		return s;

	}

	public boolean checkPostedShift(Date date, Date start, Date end, Long eid) {
		boolean result = false;
		System.out.println("IINSIDE CHECK SHIFT POSTED DAO");
		begin();
		Query q = getSession().createQuery("from Shifts where date =:date and startTime =:start and endTime= :end ");
		q.setDate("date", date);
		q.setTime("start", start);
		q.setTime("end", end);
		List<Shifts> shiftList = q.list();
		commit();
		for (Shifts s : shiftList) {
			if (s.getSupervisorStaff().getEmployer().getEmployerID() == eid) {
				result = true;
			} else {
				result = false;
			}
		}

		return result;

	}

}
