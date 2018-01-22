package com.reqman.dao;

import com.reqman.vo.zoho.subscription.hostpage.RootObject;

public interface SubscribeInterface 
{

	public int saveSubscription(RootObject rootObject, String user) throws Exception;
}
