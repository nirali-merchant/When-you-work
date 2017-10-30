package com.nirali.spring.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nirali.spring.exception.EmployerException;
import com.nirali.spring.exception.PersonException;
import com.nirali.spring.pojo.Availability;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.OpenShifts;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;

public class StudentDAO extends DAO {

	public StudentDAO() {
	}

	public List<Employer> getEmployersList() throws Exception {
		// TODO Auto-generated method stub
		try {
			begin();
			Query q = getSession().createQuery("from Employer where approved = :approved");
			q.setBoolean("approved", true);
			List<Employer> employerList = q.list();
			commit();
			return employerList;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not fetch Employers", e);
		}
	}

	public Role getRole(String roleName) {
		Query q = getSession().createQuery("from Role where roleName = :roleName");
		q.setString("roleName", roleName);
		Role role = (Role) q.uniqueResult();
		return role;
	}

	public Employer getEmployer(String employer) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");

			Query q = getSession().createQuery("from Employer where companyName = :employer ");
			q.setString("employer", employer);

			Employer emp = (Employer) q.uniqueResult();
			commit();
			return emp;
		} catch (HibernateException ex) {
			rollback();
			throw new Exception("Could not get email " + ex);
		}
	}

	public StudentStaff registerStudent(StudentStaff s) throws Exception {
		try {
			begin();
			System.out.println("inside DAO");

			StudentStaff ss = new StudentStaff();
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

	public List<Role> getEmployerRoles() throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from Role ");
			List<Role> roleList = q.list();
			commit();
			return roleList;
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not fetch Roles", e);
		}

	}

	public void setAvail(Availability avail) throws Exception {
		try {
			begin();
			getSession().save(avail);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the shifts", e);
		}
	}

	public List<ShiftTracker> getShifts(long personId) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from ShiftTracker where personID = :personId");
			q.setLong("personId", personId);
			List<ShiftTracker> stList = q.list();
			commit();
			return stList;

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the shifts", e);
		}
	}

	public List<ShiftTracker> getSwappedShifts(long personId) throws Exception {
		try {
			begin();
			Query q = getSession().createQuery("from ShiftTracker where swappedWithStudentID = :personId");
			q.setLong("personId", personId);
			List<ShiftTracker> stList = q.list();
			commit();
			return stList;

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the shifts", e);
		}
	}

	public void ack(ShiftTracker st) throws Exception {
		try {
			begin();
			getSession().save(st);
			commit();

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the shifts", e);
		}
	}

	public void ackShift(ShiftTracker st) throws Exception {
		try {
			begin();
			getSession().update(st);
			commit();

		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the shifts", e);
		}
	}

	public StudentStaff getStudent(long id) {
		Query q = getSession().createQuery("from StudentStaff where personID = :id");
		q.setLong("id", id);
		StudentStaff s = (StudentStaff) q.uniqueResult();
		return s;

	}

	public List<ShiftTracker> getAcknowledgedShifts(Long pid, Long eid) {
		begin();
		Query q = getSession()
				.createQuery("from ShiftTracker where acknowledged =:acknowledged and " + "personID != :pid");
		q.setBoolean("acknowledged", true);
		q.setLong("pid", pid);
		List<ShiftTracker> shifts = q.list();
		List<ShiftTracker> ackShifts = new ArrayList();
		for (ShiftTracker s : shifts) {
			StudentStaff student = s.getStudentStaff();
			if (student.getEmployer().getEmployerID() == eid) {
				ackShifts.add(s);
			}
		}
		
		Query q1 = getSession().createQuery("from ShiftTracker where  PersonID  = :pid ");
		q1.setLong("pid", pid);
		List<Long> shiftids= new ArrayList<Long>();
		List<ShiftTracker> ownShiftTrackerShifts = (List<ShiftTracker>)  q1.list();
		System.out.println("ownShiftTrackershifts="+ownShiftTrackerShifts.size());
		for(ShiftTracker owst : ownShiftTrackerShifts)
		{
			System.out.println("own Shift id="+owst.getShifts().getShiftID());
			shiftids.add(owst.getShifts().getShiftID());
		}
		
		List<ShiftTracker> newAckShifts = new  ArrayList<ShiftTracker>();
		
		Iterator iter  =ackShifts.iterator();
		while(iter.hasNext())
		{
			ShiftTracker shift = (ShiftTracker) iter.next();
			if(!shiftids.contains(shift.getShifts().getShiftID()))
			{
				//droppedShifts.remove(shift);
				newAckShifts.add(shift);
				
			}
		}
			
		
		
		
		
		commit();
		return newAckShifts;

	}

	public List<ShiftTracker> getDroppedShifts(Long pid, Long eid) {
		begin();
		
		
		Query q = getSession().createQuery("from ShiftTracker where dropped =:dropped and PersonID  != :pid ");
		q.setBoolean("dropped", true);
		q.setLong("pid", pid);
		
		List<ShiftTracker> shifts = q.list();
		List<ShiftTracker> droppedShifts = new ArrayList();
		for (ShiftTracker s : shifts) {
			StudentStaff student = s.getStudentStaff();
			//
			
			if (student.getEmployer().getEmployerID() == eid) {
				System.out.println("eid="+ eid);
				System.out.println("sei="+ student.getEmployer().getEmployerID() );
			
				droppedShifts.add(s);
			}
		}
		
		
		Query q1 = getSession().createQuery("from ShiftTracker where  PersonID  = :pid ");
		q1.setLong("pid", pid);
		List<Long> shiftids= new ArrayList<Long>();
		List<ShiftTracker> ownShiftTrackerShifts = (List<ShiftTracker>)  q1.list();
		System.out.println("ownShiftTrackershifts="+ownShiftTrackerShifts.size());
		for(ShiftTracker owst : ownShiftTrackerShifts)
		{
			System.out.println("own Shift id="+owst.getShifts().getShiftID());
			shiftids.add(owst.getShifts().getShiftID());
		}
		List<ShiftTracker> newDroppedShift = new  ArrayList<ShiftTracker>();
		
		Iterator iter  =droppedShifts.iterator();
		while(iter.hasNext())
		{
			ShiftTracker shift = (ShiftTracker) iter.next();
			if(!shiftids.contains(shift.getShifts().getShiftID()))
			{
				//droppedShifts.remove(shift);
				newDroppedShift.add(shift);
				
			}
		}
			
		
		
		
		commit();
		return newDroppedShift;

		// List<Shifts> droppedShifts = q.list();

		// return droppedShifts;
	}

	public List<ShiftTracker> getOpenShifts(Long pid, Long eid) {
		begin();
		 
		Query q = getSession().createQuery("from ShiftTracker where open =:open and PersonID  != :pid");
		q.setLong("pid", pid);
		q.setBoolean("open", true);

		List<ShiftTracker> shifts = q.list();
		List<ShiftTracker> openShifts = new ArrayList();
		for (ShiftTracker s : shifts) {
			StudentStaff student = s.getStudentStaff();
			if (student.getEmployer().getEmployerID() == eid) {
				openShifts.add(s);
			}
		}

		// List<Shifts> openShifts = q.list();
		commit();

		return openShifts;
	}

	public Set<OpenShifts> getRemainingOpenShifts(Long eid, Long pid) {
		System.out.println("inside remaininh open shifts");
		begin();
		Query q = getSession().createQuery("from OpenShifts ");
		List<OpenShifts> osList = q.list();
		System.out.println("osList = " + osList.size());
		Set<OpenShifts> openShiftsList = new HashSet<OpenShifts>();
		for (OpenShifts os : osList) {
			Long sid = os.getShifts().getShiftID();
			Query q1 = getSession().createQuery("from ShiftTracker where shiftID = :sid and personID = :pid");
			q1.setLong("sid", sid);
			q1.setLong("pid", pid);
			ShiftTracker st = (ShiftTracker) q1.uniqueResult();
			if (st == null) {
				Long empId = os.getShifts().getSupervisorStaff().getEmployer().getEmployerID();
				System.out.println("eid=" + eid);
				System.out.println("empId" + empId);
				if (empId == eid) {
					System.out.println("added");
					openShiftsList.add(os);
				}
			}
		}
		commit();
		return openShiftsList;
	}

	public ShiftTracker swap(Long swappedShiftID, Long pid, Long swappwdByShiftID, Long swappedByPersonID) {
		begin();
		Query q = getSession().createQuery("from ShiftTracker where shiftID = :swappedShiftID and personID = :pid ");
		q.setLong("swappedShiftID", swappedShiftID);
		q.setLong("pid", pid);
		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		st.setSwapped(true);
		st.setSwappedShiftID(swappwdByShiftID);
		st.setSwappedWithStudentID(swappedByPersonID);
		getSession().save(st);
		return st;

	}

	public ShiftTracker swapShift(Long swappedShiftID, Long pid, Long swappwdByShiftID, Long swappedByPersonID) {
		begin();
		Query q = getSession()
				.createQuery("from ShiftTracker where shiftID =:swappwdByShiftID and personID = :swappedByPersonID");
		q.setLong("swappwdByShiftID", swappwdByShiftID);
		q.setLong("swappedByPersonID", swappedByPersonID);
		ShiftTracker swappedst = (ShiftTracker) q.uniqueResult();
		swappedst.setSwappedShiftID(swappedShiftID);
		// swappedst.setSwapped(true);
		swappedst.setSwappedWithStudentID(pid);
		getSession().save(swappedst);
		commit();
		return swappedst;
	}

	public ShiftTracker getShiftToBeAcknowledged(Long shiftID, Long pid) {
		begin();
		Query q = getSession().createQuery("from ShiftTracker where shiftID = :shiftID and personID = :pid");
		q.setLong("shiftID", shiftID);
		q.setLong("pid", pid);
		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		st.setAcknowledged(true);
		st.setOpen(false);
		commit();
		return st;
	}

	public List<ShiftTracker> getAllShifts(long pid) {
		begin();
		Query q = getSession().createQuery("from ShiftTracker where  swappedWithStudentID = :pid");
		q.setLong("pid", pid);
		List<ShiftTracker> stList = (List<ShiftTracker>) q.list();
		commit();
		return stList;

	}

	public ShiftTracker getSelectedOpenShift(Long shiftID, Long personID, Long pid) {

		begin();
		Query q = getSession().createQuery("from ShiftTracker where shiftID =:shiftID and personID =:personID");
		q.setLong("shiftID", shiftID);
		q.setLong("personID", personID);
		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		if (st != null) {
			getSession().delete(st);
		}
		Query q1 = getSession().createQuery("from Shifts where shiftID = :shiftID");
		q1.setLong("shiftID", shiftID);
		Shifts shift = (Shifts) q1.uniqueResult();
		System.out.println("*****shiftID=" + shift.getShiftID());
		// System.out.println(("****personid"+ person);
		Query q2 = getSession().createQuery("from StudentStaff where personID = :pid");
		q2.setLong("pid", pid);
		StudentStaff studentStaff = (StudentStaff) q2.uniqueResult();

		ShiftTracker newShiftTracker = new ShiftTracker();
		newShiftTracker.setShifts(shift);
		newShiftTracker.setStudentStaff(studentStaff);
		System.out.println("&&&&&&" + studentStaff.getPersonID());
		newShiftTracker.setAcknowledged(true);
		getSession().save(newShiftTracker);
		return newShiftTracker;
	}

	public ShiftTracker getSelectedRemainingOpenShift(Long shiftID, Long pid) {

		begin();

		Query q1 = getSession().createQuery("from Shifts where shiftID = :shiftID");
		q1.setLong("shiftID", shiftID);
		Shifts shift = (Shifts) q1.uniqueResult();
		System.out.println("*****shiftID=" + shift.getShiftID());
		// System.out.println(("****personid"+ person);
		Query q2 = getSession().createQuery("from StudentStaff where personID = :pid");
		q2.setLong("pid", pid);
		StudentStaff studentStaff = (StudentStaff) q2.uniqueResult();

		ShiftTracker newShiftTracker = new ShiftTracker();
		newShiftTracker.setShifts(shift);
		newShiftTracker.setStudentStaff(studentStaff);
		System.out.println("&&&&&&" + studentStaff.getPersonID());
		newShiftTracker.setAcknowledged(true);
		getSession().save(newShiftTracker);
		commit();

		// delete one shift from Open Shifts which got added t ShiftTracker
		begin();
		System.out.println("After this");
		Query q3 = getSession().createQuery("from OpenShifts where shiftID = :shiftID");
		q3.setLong("shiftID", shiftID);
		OpenShifts o = (OpenShifts) q3.setMaxResults(1).uniqueResult();
		System.out.println("OPENSHIFT ID= " + o.getOpenshiftID());
		getSession().delete(o);
		commit();

		return newShiftTracker;
	}

	public ShiftTracker getSelectedDropShift(Long shiftID, Long personID, Long pid) {
		begin();
		Query q = getSession().createQuery("from ShiftTracker where shiftID =:shiftID and personID =:personID");
		q.setLong("shiftID", shiftID);
		q.setLong("personID", personID);
		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		if (st != null) {
			getSession().delete(st);
		}
		Query q1 = getSession().createQuery("from Shifts where shiftID = :shiftID");
		q1.setLong("shiftID", shiftID);
		Shifts shift = (Shifts) q1.uniqueResult();
		System.out.println("*****shiftID=" + shift.getShiftID());
		// System.out.println(("****personid"+ person);
		Query q2 = getSession().createQuery("from StudentStaff where personID = :pid");
		q2.setLong("pid", pid);
		StudentStaff studentStaff = (StudentStaff) q2.uniqueResult();

		ShiftTracker newShiftTracker = new ShiftTracker();
		newShiftTracker.setShifts(shift);
		newShiftTracker.setStudentStaff(studentStaff);
		System.out.println("&&&&&&" + studentStaff.getPersonID());
		newShiftTracker.setAcknowledged(true);
		getSession().save(newShiftTracker);
		return newShiftTracker;
	}

	public boolean checkPostedAvailability(Date date, Date start, Date end, Long pid) {
		boolean result = false;
		System.out.println("IINSIDE CHECK Availability POSTED DAO");
		begin();
		Query q = getSession()
				.createQuery("from Availability where date =:date and startTime >=:start and endTime <= :end ");
		q.setDate("date", date);
		q.setTime("start", start);
		q.setTime("end", end);
		List<Availability> availList = q.list();
		System.out.println("****" + availList.size());
		if (availList.size() != 0) {
			for (Availability a : availList) {
				System.out.println("**" + a.getAvailID());
				Set<StudentStaff> s = a.getStudentStaff();
				for (StudentStaff stud : s) {
					System.out.println("**" + stud.getPersonID());
					System.out.println("pid=" + pid);
					if (stud.getPersonID() == pid) {

						return false;
					} else {
						return true;
					}
				}
			}
			commit();
		} else {
			result = true;
		}

		return result;

	}

	public Set<Availability> getAvail(Long pid, Date date) {

		begin();
		Query q = getSession().createQuery("from Availability where date >= :date");
		q.setDate("date", date);

		List<Availability> avail = q.list();
		Set<Availability> availSet = new HashSet();
		for (Availability a : avail) {
			Set<StudentStaff> s = a.getStudentStaff();

			for (StudentStaff stud : s) {
				System.out.println("**" + stud.getPersonID());
				System.out.println("pid=" + pid);
				if (stud.getPersonID() == pid) {
					availSet.add(a);

				}
			}
		}

		return availSet;
	}

	public Shifts getShiftByID(Long shiftID) {
		begin();
		Query q = getSession().createQuery("from Shifts where shiftID = :shiftID");
		q.setLong("shiftID", shiftID);
		Shifts s = (Shifts) q.uniqueResult();
		commit();
		return s;

	}

	public void getSwappedInitiatorShift(Long initiatorShiftid, Long initiatorPersonid) {
		begin();
		Query q = getSession()
				.createQuery("from ShiftTracker where shiftID = :initiatorShiftid and personID =  :initiatorPersonid");
		q.setLong("initiatorShiftid", initiatorShiftid);
		q.setLong("initiatorPersonid", initiatorPersonid);

		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		getSession().delete(st);
		System.out.println("deleted");

		/*
		 * List<ShiftTracker> stList = q.list(); for(ShiftTracker st : stList) {
		 * StudentStaff ss = st.getStudentStaff(); if(ss.getPersonID() ==
		 * initiatorPersonid){ getSession().delete(st);
		 * System.out.println("deleted"); } }
		 */
		commit();

	}

	public void getSwappedSwappedWithShift(Long swappedShiftid, Long swappedPersonid) {
		begin();
		Query q = getSession()
				.createQuery("from ShiftTracker where shiftID = :swappedShiftid  and personID = :swappedPersonid ");
		q.setLong("swappedShiftid", swappedShiftid);
		q.setLong("swappedPersonid", swappedPersonid);

		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		getSession().delete(st);
		System.out.println("deleted");
		/*
		 * List<ShiftTracker> stList = q.list(); for(ShiftTracker st : stList) {
		 * StudentStaff ss = st.getStudentStaff(); if(ss.getPersonID() ==
		 * swappedPersonid){ System.out.println("the initiator shift id  = " );
		 * getSession().delete(st); System.out.println("deleted"); } }
		 */
		commit();
	}

	public void swapActualShifts(Long initiatorShiftid, Long initiatorPersonid, Long swappedShiftid,
			Long swappedPersonid) {
		begin();
		// Query to get Shift Object
		Query q1 = getSession().createQuery("from Shifts where shiftID = :initiatorShiftid");
		q1.setLong("initiatorShiftid", initiatorShiftid);
		Shifts s1 = (Shifts) q1.uniqueResult();
		System.out.println(" s1  id = " + s1.getShiftID());

		// Query to get swapped person object
		Query q2 = getSession().createQuery("from StudentStaff where personID = :swappedPersonid");
		q2.setLong("swappedPersonid", swappedPersonid);
		StudentStaff ss1 = (StudentStaff) q2.uniqueResult();
		System.out.println(" ss1  id = " + ss1.getPersonID());

		// Make a new ShiftTracer object
		ShiftTracker st1 = new ShiftTracker();
		st1.setShifts(s1);
		st1.setStudentStaff(ss1);
		st1.setAcknowledged(true);
		getSession().save(st1);
		System.out.println("new object 1 made");

		// Query to get Shift Object
		Query q3 = getSession().createQuery("from Shifts where shiftID = :swappedShiftid");
		q3.setLong("swappedShiftid", swappedShiftid);
		Shifts s2 = (Shifts) q3.uniqueResult();
		System.out.println(" s2  id = " + s2.getShiftID());

		// Query to get swapped person object
		Query q4 = getSession().createQuery("from StudentStaff where personID = :initiatorPersonid");
		q4.setLong("initiatorPersonid", initiatorPersonid);
		StudentStaff ss2 = (StudentStaff) q4.uniqueResult();

		System.out.println(" ss2  id = " + ss2.getPersonID());
		// Make a new ShiftTracer object
		ShiftTracker st2 = new ShiftTracker();
		st2.setShifts(s2);
		st2.setAcknowledged(true);
		st2.setStudentStaff(ss2);
		getSession().save(st2);
		System.out.println("new object 2 made");

		commit();

	}

	public void denyswapActualShifts(Long initiatorShiftid, Long initiatorPersonid, Long swappedShiftid,
			Long swappedPersonid) {
		begin();
		// Query to get Shift Object
		begin();
		Query q = getSession()
				.createQuery("from ShiftTracker where shiftID = :swappedShiftid  and personID = :swappedPersonid ");
		q.setLong("swappedShiftid", swappedShiftid);
		q.setLong("swappedPersonid", swappedPersonid);
		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		st.setSwapped(false);
		st.setSwappedShiftID(0);
		st.setSwappedWithStudentID(0);
		getSession().save(st);

		Query q1 = getSession()
				.createQuery("from ShiftTracker where shiftID = :initiatorShiftid  and personID = :initiatorPersonid ");
		q1.setLong("initiatorShiftid", initiatorShiftid);
		q1.setLong("initiatorPersonid", initiatorPersonid);
		ShiftTracker st1 = (ShiftTracker) q1.uniqueResult();
		st1.setSwapped(false);
		st1.setSwappedShiftID(0);
		st1.setSwappedWithStudentID(0);
		getSession().save(st1);

		commit();

	}

}
