package com.nirali.spring.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.StudentStaff;

public class AdminDAO extends DAO {

	public AdminDAO() {

	}

	public void insertRoles(String roleName) {
		begin();
		Role role = new Role();
		role.setRoleName(roleName);
		getSession().save(role);
		commit();

	}

	public List<Employer> getPendingEmployers() {
		begin();
		Query q = getSession().createQuery("from Employer where approved = :approved");
		q.setBoolean("approved", false);
		List<Employer> employerList = q.list();
		commit();
		return employerList;

	}

	public List<Employer> getApprovedEmployers() {
		begin();
		Query q = getSession().createQuery("from Employer where approved = :approved");
		q.setBoolean("approved", true);
		List<Employer> employerList = q.list();
		commit();
		return employerList;

	}

	public Employer getEmployer(Long employerid) {
		begin();
		Query q = getSession().createQuery("from Employer where employerID = :employerid");
		q.setLong("employerid", employerid);
		Employer e = (Employer) q.uniqueResult();
		// e.setApproved(true);
		commit();
		return e;

	}

	public Employer getApproval(Long employerid) {
		begin();
		Query q = getSession().createQuery("from Employer where employerID = :employerid");
		q.setLong("employerid", employerid);
		Employer e = (Employer) q.uniqueResult();
		e.setApproved(true);
		commit();
		return e;

	}

	public List<Role> checkRole(String roleName) {
		begin();
		Criteria crit = getSession().createCriteria(Role.class);
		crit.add(Restrictions.eq("roleName", roleName));
		List<Role> roles = crit.list();
		return roles;

	}

	public List<Role> getRoles() {
		begin();
		Query q = getSession().createQuery("from Role ");
		List<Role> roles = q.list();
		return roles;

	}

	public void deleteRole(String roleName) {
		begin();
		Query q = getSession().createQuery("from Role where roleName = :roleName");
		q.setString("roleName", roleName);
		Role role = (Role) q.uniqueResult();
		getSession().delete(role);
		commit();

	}

	public List<Employer> getAllEmployers() {
		begin();
		Query q = getSession().createQuery("from Employer where approved != :approved");
		q.setBoolean("approved", false);
		List<Employer> employerList = q.list();
		commit();
		return employerList;

	}

}
