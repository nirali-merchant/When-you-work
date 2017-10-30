package com.nirali.spring.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.HibernateException;

import com.nirali.spring.exception.EmployerException;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;

public class EmployerDAO extends DAO {

	public EmployerDAO() {

	}

	public Employer registerEmployer(Employer e) throws EmployerException {

		try {

			System.out.println("inside DAO");
			Employer employer = new Employer();
			System.out.println("**" + e.getCompanyName());
			employer.setCompanyName(e.getCompanyName());
			employer.setAddress(e.getAddress());
			System.out.println("**" + e.getAddress());
			employer.setPhoneNo(e.getPhoneNo());
			System.out.println("**" + e.getPhoneNo());
			employer.setEmailAddress(e.getEmailAddress());

			begin();
			getSession().save(employer);
			System.out.println("*%%%");
			commit();

			return employer;

		} catch (HibernateException ex) {
			rollback();
			throw new EmployerException("Exception while creating employer: " + ex.getMessage());
		}

	}

}
