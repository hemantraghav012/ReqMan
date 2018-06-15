package com.reqman.daoimpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.UploadedFile;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.Publicdomaininterface;
import com.reqman.pojo.Account;
import com.reqman.pojo.Publicemaildomains;
import com.reqman.pojo.Request;
import com.reqman.pojo.Userfriendlist;
import com.reqman.pojo.Users;
import com.reqman.vo.AccountVo;
import com.reqman.vo.NewrequestVo;
import com.reqman.vo.PublicemaildomainVo;
import com.reqman.vo.UserVo;



public class PublicdomainImpl implements Publicdomaininterface{
	

	@SuppressWarnings("unused")
	@Override
	public int save(List<PublicemaildomainVo> publicdonainList, String userName) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		int result = 0;
		PublicemaildomainVo publicemaildomainVo1 = null;
		List<String> domainname1 = new ArrayList<String>();
		Publicemaildomains publicemaildomain = null;
		String name = "";
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();

			for (PublicemaildomainVo publicemaildomainVo : publicdonainList) {
				name = publicemaildomainVo.getName();

				publicemaildomain = (Publicemaildomains) session.createCriteria(Publicemaildomains.class)
						.add(Restrictions.eq("name", name.toLowerCase().trim()).ignoreCase()).uniqueResult();

				if (publicemaildomain != null && publicemaildomain.getName() != null
						&& publicemaildomain.getName().trim().equalsIgnoreCase(name.trim())) {
					publicemaildomain.setStatus(true);

					session.update(publicemaildomain);

					result = 1;
				}

				else {

					publicemaildomain = new Publicemaildomains();
					publicemaildomain.setCreatedby("system");
					publicemaildomain.setName(name);
					publicemaildomain.setDatecreated(new Date());
					publicemaildomain.setStatus(true);

					session.save(publicemaildomain);
				}

			}

			tx.commit();
			result = 3;

		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			result = 2;
		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}
/*
	@Override
	public PublicemaildomainVo getemaildomainfile() throws Exception {
		// TODO Auto-generated method stub

		Session session = null;
		Transaction tx = null;
		PublicemaildomainVo publicemaildomainVo = new PublicemaildomainVo();
		Publicemaildomains publicemaildomain = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			publicemaildomain = (Publicemaildomains) session.createCriteria(
					Publicemaildomains.class).uniqueResult();

			if (publicemaildomain != null) {
				// publicemaildomainVo.setFile(file);
				// publicemaildomainVo.setFileName(fileName);

				tx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null)
				session.close();
		}

		return publicemaildomainVo;

	}
*/
	
	@Override
	public int quickrequestcheckval(String emailid) throws Exception {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		int result = 0;
		Publicemaildomains publicemaildomains = null;

		String emailArr[] = {};
		try {

			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			if (emailid != null) {
				emailArr = emailid.split("@");
				if (emailArr != null && emailArr.length >= 2) {

					Criteria crit = session.createCriteria(Publicemaildomains.class);

					crit.add(Restrictions.eq("name", emailArr[1].toLowerCase().trim()).ignoreCase());

					publicemaildomains = (Publicemaildomains) crit.uniqueResult();
				}
				if (publicemaildomains != null) {
					result = 1;
				}

				else {
					result = 3;
				}

				tx.commit();

			}

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			result = 4;
			throw new Exception(e);
		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}

}
