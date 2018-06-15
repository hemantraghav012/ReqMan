package com.reqman.beans;



import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.reqman.dao.Publicdomaininterface;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.PublicdomainImpl;
import com.reqman.daoimpl.UserDetailsImpl;


@ManagedBean(name = "accountadminregister", eager = true)
public class accountadminregisterbean {

	private String emailid;
	private String firstname;
	private String lastname;
	private String shortname;
	private String hashkey;
	private String emailstatus;
    private String password;
    private String organizationname;
    
	private Publicdomaininterface publicdomainInterface = new PublicdomainImpl();
	
	public void checkdomain() {
		int result = 0;

		try {

			System.out.println(emailid);

			if (emailid != null) {
				result = publicdomainInterface.quickrequestcheckval(emailid);

				if (result == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Please do not use public domain", "Please use own company domain"));
				}

				if (result == 3) {
					{

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Server Error " + e.getMessage(), "Server Error " + e.getMessage()));

		}

	}

	
	
	public String submit()
	{
		
		//is declared as type UserDetailsInterface(interface) and the object it references is of type UserDetailsImpl(class).
		UserDetailsInterface userImpl = new UserDetailsImpl();
		
		int result = 0;
		try{
		//Pass all value through registerbean class to userImpl Interface and call saveUser() method
			result = userImpl.saveAccountadminUser(emailid.toLowerCase().trim(), password, firstname, lastname, shortname,hashkey,organizationname);
		
			//boolean valid = LoginDAO.validate(user, pwd);
			if (result == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Admin Role created  successfully",
								""));
				return "login";
			}
			if (result == 2) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Inactive",
								"User already created, please activate the user credentials before login the sytem"));
				return "accountadminregister";
			}
			if (result == 4) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Internal Error",
								"Please check the server logs"));
				return "accountadminregister";
			}
			if (result == 6) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"User Exit",
								"User already created, please login with your credentials"));
				return "accountadminregister";
			}
			if (result == 7) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Please do not use public domain", "Please use own company domain"));
				return "accountadminregister";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Internal Error",
							"Please check the server logs"));
			return "accountadminregister";
		}
	
		return "sendemailbutton";
	}
	
	
	
	
	
	
	
	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public Publicdomaininterface getPublicdomainInterface() {
		return publicdomainInterface;
	}

	public void setPublicdomainInterface(Publicdomaininterface publicdomainInterface) {
		this.publicdomainInterface = publicdomainInterface;
	}



	public String getHashkey() {
		return hashkey;
	}



	public void setHashkey(String hashkey) {
		this.hashkey = hashkey;
	}



	public String getEmailstatus() {
		return emailstatus;
	}



	public void setEmailstatus(String emailstatus) {
		this.emailstatus = emailstatus;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getOrganizationname() {
		return organizationname;
	}



	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
	
	
	
	
	
}
