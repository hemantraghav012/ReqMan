package com.reqman.dao;

public interface PaytabsInterface {

	int getUserInformation(String firstname, String lastname, String emailid, String billingaddress, String country,
			String city, String state, Integer countrycode, Integer telephone, Integer postalcode) throws Exception ;

}
