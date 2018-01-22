package com.reqman.rest.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reqman.common.ReadConfigurations;
import com.reqman.vo.zoho.subscription.hostpage.RootObject;

public class UserProfileAPIClient {

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
		List<RootObject> roleList = client.target(ReadConfigurations.ZOHO_HOSTPAGE_URL+ReadConfigurations.hostpageid)
	            				//.path("rolesservices/getroles")	            
					            .request(MediaType.APPLICATION_JSON)
					            .header("Authorization", ReadConfigurations.Authorization)
					            .get(list);

		
		
		return roleList;
	}
	
	public RootObject getUserSubscription(String hostpageId) throws Exception
	{
		RootObject rootObject = new RootObject();
		Client client = ClientBuilder.newClient();
		Response response = client
				.target(ReadConfigurations.ZOHO_HOSTPAGE_URL
						+ hostpageId)
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", ReadConfigurations.Authorization)
				.get();
		String xx = response.readEntity(String.class);
		System.out.println("Response: " + response.getStatus() + " - " + xx);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
		
		rootObject = gson.fromJson(xx, RootObject.class);

		//System.out.println("--rootObject-->"+rootObject);
		
		return rootObject;
	}
	
	
	public static void main(String agrs[])
	{
		
		UserProfileAPIClient client = new UserProfileAPIClient();
		RootObject rb = null;
		try
		{
			System.out.println("---->"+rb);
			rb = client.getUserSubscription(ReadConfigurations.hostpageid);
			System.out.println(""+rb);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
