package com.nirali.spring.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nirali.spring.exception.EmployerException;
import com.nirali.spring.exception.PersonException;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;

public class UserDAO extends DAO {

	public UserDAO() {
	}

	public Person login(Person p) throws PersonException {
		try {
			begin();
			System.out.println("inside DAO");

			String email = p.getEmailID();
			String password = p.getPassword();

			Query q = getSession().createQuery("from Person where emailID = :email and password = :password");
			q.setString("email", email);
			q.setString("password", password);
			Person person = (Person) q.uniqueResult();
			commit();
			return person;
		} catch (HibernateException e) {
			rollback();
			throw new PersonException("Could not get email " + e);
		}
	}

	public List<Person> getEmployees() {
		int id = 1;
		begin();
		Query q = getSession().createQuery("from Person where roleID != :id");
		q.setInteger("id", 1);
		List<Person> userList = q.list();
		commit();
		return userList;
	}

	/*
	 * public Person registerEmployee(Person p) throws PersonException { try {
	 * begin(); System.out.println("inside DAO");
	 * 
	 * Person person = new Person(); person.setFirstName(p.getFirstName());
	 * person.setLastName(p.getLastName()); person.setEmailID(p.getEmailID());
	 * person.setPassword(p.getPassword()); person.setRole(p.getRole());
	 * person.setEmployer(p.getEmployer());
	 * if(p.getRole().getRoleName().equalsIgnoreCase("Student")) { StudentStaff
	 * sf = new StudentStaff("tp"); sf.setPersonID(person.getPersonID()); sf. }
	 * else if(p.getRole().getRoleName().equalsIgnoreCase("Supervisor")) {
	 * 
	 * } getSession().save(person); commit();
	 * 
	 * 
	 * 
	 * return person; } catch (HibernateException e) { rollback(); throw new
	 * PersonException("Exception while creating employee:" + e); } }
	 */

	/*
	 * public List<Role> getRoleList() throws HibernateException{ // TODO
	 * Auto-generated method stub try { begin(); Query q =
	 * getSession().createQuery("from Role where employer "); List<Role>
	 * employerList = q.list(); commit(); return employerList; } catch
	 * (HibernateException e) { rollback(); throw new
	 * EmployerException("Could not delete Employer", e); }
	 */

	public List<Role> getEmployerId(long employerId) throws EmployerException {
		List<Long> roleIdList = null;
		List<Role> roles;
		try {
			begin();
			String queryString = "SELECT u FROM Employer u";
			Query consulta = getSession().createQuery(queryString);
			roles = (List<Role>) consulta.list();
			/*
			 * Query q = getSession().
			 * createQuery("from employerroles where employerID = :employerid "
			 * ); q.setLong("employerId", employerId); roleIdList = q.list();
			 */
			commit();
		} catch (HibernateException ex) {
			rollback();
			throw new EmployerException("Could not get Roles " + ex);
		}
		// return roleIdList;
		return roles;

	}

	public Role getRole(long roleId) throws EmployerException {
		Role role = null;

		try {
			begin();
			Query q = getSession().createQuery("from Role where RoleID = :roleId ");
			q.setLong("RoleID", roleId);
			role = (Role) q.uniqueResult();

			commit();

		} catch (HibernateException ex) {
			rollback();
			throw new EmployerException("Could not get Roles " + ex);
		}
		return role;

	}

	public Shifts setAvailability(Shifts s, StudentStaff ss) throws Exception {

		System.out.println("insde shifts dao");
		StudentStaff ss1 = null;

		try {
			begin();
			System.out.println("inside DAO");
			Shifts shifts = new Shifts();

			shifts.setDate(s.getDate());
			// shifts.getStudentStaff().add(ss);
			// System.out.println("ss size="+ss.size());
			// Iterator itr = ss.iterator();
			// StudentStaff s1 = new StudentStaff();
			/*
			 * s1.setPersonID(pid); ss.add(s1); s.setStudentStaff(ss);
			 */
			getSession().save(shifts);
			commit();
			System.out.println("shifts saved");

			/*
			 * while(itr.hasNext()) {
			 * //System.out.println("shift-person"+itr.next()); ss1 = new
			 * StudentStaff(); ss1.setPersonID(personID); } S ss.add(e)
			 */
			/*
			 * begin(); getSession().update(ss); commit();
			 */

			return shifts;
		} catch (HibernateException e) {
			rollback();
			throw new PersonException("Exception while inserting availability:" + e);
		}

	}

	public void update(StudentStaff stud) throws Exception {
		try {
			begin();
			getSession().update(stud);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the stud", e);
		}
	}

	public void update(Shifts shifts) throws Exception {
		try {
			begin();
			getSession().update(shifts);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the stud", e);
		}
	}

	;

	public void setShift(Shifts shifts) throws Exception {
		try {
			begin();
			getSession().save(shifts);
			commit();
		} catch (HibernateException e) {
			rollback();
			throw new Exception("Could not save the shifts", e);
		}
	}

	public Person getPerson(long pid) {
		Person p = null;
		Query q = getSession().createQuery("from Person where personID = :pid");
		q.setLong("pid", pid);
		p = (Person) q.uniqueResult();
		return p;
	}

	public boolean checkUniqueEmail(String email, String companyName) {
		begin();
		Criteria crit = getSession().createCriteria(Person.class);
		crit.add(Restrictions.ilike("emailID", email));

		// Criteria employer = crit.createCriteria("employer");
		// employer.add(Restrictions.eq("companyName", companyName));
		Person p = (Person) crit.uniqueResult();
		if (p == null) {
			return true;
		} else {
			return false;
		}

	}

}
