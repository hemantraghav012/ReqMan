package com.reqman.rest.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reqman.beans.PayTabPaymentbean;
import com.reqman.common.ReadConfigurations;
import com.reqman.dao.PaytabsInterface;
import com.reqman.vo.zoho.subscription.hostpage.RootObject;

public class PayTabAPIClient2 {

	public List<RootObject> getUserInfoById(Integer userId,String token)
	{
		/*Client client = ClientBuilder.newClient().register(RootObject.class);
		GenericType<List<RootObject>> list = new GenericType<List<RootObject>>() {};
		@SuppressWarnings("unchecked")
		List<RootObject> userInfo = client.target(ReadConfigurations.ZOHO_HOSTPAGE_URL+ReadConfigurations.hostpageid)
	            				//.path("userprofiles/"+userId)	            
					            .request(MediaType.APPLICATION_JSON)
					            .header("Authorization", ReadConfigurations.Authorization)
					            .get(List.class);*/
		
		Client client = ClientBuilder.newClient().register(RootObject.class);
		GenericType<List<RootObject>> list = new GenericType<List<RootObject>>() {};
		List<RootObject> roleList = client.target(ReadConfigurations.paypagecreateurl+ReadConfigurations.hostpageid)
	            				//.path("rolesservices/getroles")	            
					            .request(MediaType.APPLICATION_JSON)
					            .header("Authorization", ReadConfigurations.Authorization)
					            .get(list);

		
		
		return roleList;
	}
	
	public Map<String, String> getUserSubscription(String hostpageId, String firstname, String lastname, String emailid, String billingaddress, String country, String city, String state, String countrycode, String telephone, String postalcode, String amount, String description, String reference) throws Exception
	{
		
		Form form = new Form();
		form.param("merchant_email", "naveen0480@gmail.com");
		form.param("secret_key", "XZK6FQKuVfzdQ7GtECCUkPATcXnyxocvoXB4NmyJB8WsfDWTgyOAnouAlS9ThloFj0J6miQYqNqGqmAHVoijMMT8F31WopBIplPM");
		form.param("site_url", "http://localhost:8080/ReqMan/faces/login.xhtml");
		form.param("return_url", "http://localhost:8080/ReqMan/faces/login.xhtml");
		form.param("reference_no", reference);
		form.param("title", description);
		form.param("products_per_title", description);
		form.param("quantity", "1");
		form.param("unit_price", amount);
		form.param("other_charges", "0");
		form.param("amount", amount);
		form.param("discount", "0");
		form.param("currency", "USD");
		form.param("cc_first_name", firstname);
		form.param("cc_last_name", lastname);
		form.param("email", emailid);		
		form.param("cc_phone_number", countrycode);
		form.param("phone_number", telephone);
		form.param("billing_address", billingaddress);
		form.param("city", city);
		form.param("state", state);
		form.param("postal_code", postalcode);
		form.param("country", country);
		form.param("address_shipping", billingaddress);
		form.param("city_shipping", city);
		form.param("state_shipping", state);
		form.param("postal_code_shipping", postalcode);
		form.param("country_shipping", country);
		form.param("msg_lang", "english");
		form.param("cms_with_version", "API-PHP");
		form.param("ip_merchant", "10.10.10.10");
		form.param("ip_customer", "11.11.11.11");
		form.param("is_tokenization", "TRUE");
		form.param("is_existing_customer", "FALSE");




		
	
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ReadConfigurations.paypagecreateurl).path("create_pay_page");
		 
		 
		String xx = target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
		    		String.class);
		
		


System.out.println("hi"+xx);
		

		
/*		Client client = ClientBuilder.newClient();
		
		WebTarget webTarget = client.target(ReadConfigurations.paypagecreateurl);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.MULTIPART_FORM_DATA);
		Response response = invocationBuilder.post(Entity.entity(paytabpojo, MediaType.TEXT_PLAIN));
*/		
		
		/*Entity<Paytabpojo> entity = Entity.entity(paytabpojo,
                MediaType.APPLICATION_JSON);
		System.out.println("");
		Response response = client
				.target(ReadConfigurations.paypagecreateurl)
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(entity, MediaType.APPLICATION_JSON));*/
		
		/*String xx = response.readEntity(String.class);
		System.out.println("Response: " + response.getStatus() + " - " + xx);*/
		
	/*	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		
		rootObject = gson.fromJson(xx, RootObject.class);
*/
		//System.out.println("--rootObject-->"+rootObject);
		
readJson(xx);

return readJson(xx);
	}
	
	
	
	
	
	
	public Map<String, String> readJson(String file) {
		String author = "";
		Map<String, String> mapSetKeyValue = new HashMap<String, String>();
        try {
            System.out.println("Reading JSON file from Java program");
            Object obj=JSONValue.parse(file);  
            JSONObject jsonObject = (JSONObject) obj;  
          

            String title = (String) jsonObject.get("response_code");
            author = (String) jsonObject.get("payment_url");
            String price = (String) jsonObject.get("result");

            System.out.println("response_code: " + title);
            System.out.println("payment_url: " + author);
            System.out.println("result: " + price);
            mapSetKeyValue.put(author, title);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return mapSetKeyValue;
    }
	
	
	
	/*
	 * public static void main(String agrs[]) {
	 * 
	 * PayTabAPIClient2 client = new PayTabAPIClient2(); RootObject rb = null;
	 * String xx = ""; try { System.out.println("---->"+rb); //xx =
	 * client.getUserSubscription(ReadConfigurations.hostpageid);
	 * System.out.println(""+xx); } catch(Exception e) { e.printStackTrace(); }
	 * }
	 * 
	 */

	
	
}
