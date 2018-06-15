package com.reqman.beans;

	import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
	import javax.faces.application.FacesMessage;
	import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
	import javax.servlet.http.HttpSession;

import com.reqman.common.ReadConfigurations;
import com.reqman.dao.PaytabsInterface;
import com.reqman.dao.UserDetailsInterface;
	import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.rest.client.PayTabAPIClient2;
import com.reqman.util.CommonConstants;
import com.reqman.util.SessionUtils;
import com.reqman.vo.PaytabsVo;

	@ManagedBean(name = "paytabsbean", eager = true)
	@RequestScoped
	public class PayTabPaymentbean implements Serializable {

		private static final long serialVersionUID = 1094801825228386363L;
		
		private String firstname;
		private String lastname;
		private String emailid;
		private String billingaddress;
		private String country;
		private String city;
		private String state;
		private String countrycode;
		private String telephone;
		private String postalcode;
		private String description;
		private String amount;
		private String reference;
		
		PayTabAPIClient2 payTabAPIClient2 = new PayTabAPIClient2();
		
		//public static final String SCHEDULER = "scheduler"
		public String individualInformationSubmit(){	
			
			
			try {
				reference = "";
				description = "";
				description = CommonConstants.INDIVIDUAL;
				
				if(amount.equals("10")){
					reference = CommonConstants.MONTHLY; 
				}else if(amount.equals("100")){
					reference = CommonConstants.ANNUALLY;
				}
				
				
				
				Map<String, String> getMapValue = payTabAPIClient2.getUserSubscription(ReadConfigurations.hostpageid,firstname,lastname,emailid,billingaddress,country,city,state,countrycode,telephone,postalcode,amount,description,reference);
				
				for (Map.Entry<String, String> m : getMapValue.entrySet()) {
				String url = (String) m.getKey();
				String response_code = (String) m.getValue();
				
				
				
				if(response_code.equals("4012")){
					
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect(url);
			
				}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Some thing is worng ",  "The error code is " + response_code));
				}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		
			return "paytabspage.xhtml";
		}
		
		
	public String organizationInformationSubmit(){


			try {
				reference = "";
				description = "";
				description = CommonConstants.ORGANIZATION;
				
				if(amount.equals("100")){
					reference = CommonConstants.MONTHLY; 
				}else if(amount.equals("1000")){
					reference = CommonConstants.ANNUALLY;
				}
				
				Map<String, String> getMapValue = payTabAPIClient2.getUserSubscription(ReadConfigurations.hostpageid,firstname,lastname,emailid,billingaddress,country,city,state,countrycode,telephone,postalcode,amount,description,reference);
				
				for (Map.Entry<String, String> m : getMapValue.entrySet()) {
				String url = (String) m.getKey();
				String response_code = (String) m.getValue();
				
				
				
				if(response_code.equals("4012")){
					
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
				externalContext.redirect(url);
			
				}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Some thing is worng ",  "The error code is " + response_code));
				}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		
			return "paytabspage.xhtml";
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
		public String getEmailid() {
			return emailid;
		}
		public void setEmailid(String emailid) {
			this.emailid = emailid;
		}
		public String getBillingaddress() {
			return billingaddress;
		}
		public void setBillingaddress(String billingaddress) {
			this.billingaddress = billingaddress;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		
		public String getCountrycode() {
			return countrycode;
		}



		public void setCountrycode(String countrycode) {
			this.countrycode = countrycode;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getPostalcode() {
			return postalcode;
		}


		public void setPostalcode(String postalcode) {
			this.postalcode = postalcode;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}


		public String getAmount() {
			return amount;
		}


		public void setAmount(String amount) {
			this.amount = amount;
		}


		public String getReference() {
			return reference;
		}


		public void setReference(String reference) {
			this.reference = reference;
		}


		
		
		
		
		
		
		
	}
