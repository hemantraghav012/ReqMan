package com.reqman.daoimpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.reqman.common.HibernateUtil;
import com.reqman.dao.HomeInterface;
import com.reqman.daoimpl.query.Homerequestquery;
import com.reqman.pojo.Users;
import com.reqman.vo.CategoryVo;
import com.reqman.vo.HomeVo;

public class HomeImpl implements HomeInterface {
	private final String schemaName = HibernateUtil.schemaName;
	
	
	@SuppressWarnings("null")
	@Override
	public List<HomeVo> getrequestDetailsinhomepage(String userName) throws Exception {
		// TODO Auto-generated method stub
		List<HomeVo> requestinhomeList = new ArrayList<HomeVo>();			
			Homerequestquery homerequestquery = new Homerequestquery();
			HomeVo homeVo = null; 
		BigInteger total_pending_request_onteammember = null;
		BigInteger total_inprogress_request_onteammember = null;
		BigInteger total_overdue_request_onteammember = null;
		BigInteger total_delay_request_onteammember = null;
		BigInteger total_ontarget_request_onteammember = null;
		BigInteger total_request_onteammember = null;	
		BigInteger complete_request_onteammember = null;
		BigInteger total_hold_request_onteammember = null;
		BigInteger total_close_request_onteammember = null;
		BigInteger total_cancel_request_onteammember = null;
		BigInteger total_accept_request_onteammember = null;
	
		BigInteger 	total_return_request_onteammember = null;
		BigInteger total_pending_request_onyou = null;		
		BigInteger total_inprogress_request_onyou = null;
		BigInteger total_overdue_request_onyou = null;
		BigInteger total_delay_request_onyou = null;
		BigInteger total_ontarget_request_onyou = null;
		BigInteger total_request_onyou = null;
		BigInteger complete_request_onyou = null;
		BigInteger total_hold_request_onyou = null;
		BigInteger total_close_request_onyou = null;
		BigInteger total_cancele_request_onyou = null;
		BigInteger total_accept_request_onyou = null;
		BigInteger total_return_request_onyou = null;
			try {
				
				total_pending_request_onteammember = homerequestquery.pendingrequest_onteammemberquery(userName);
				total_inprogress_request_onteammember = homerequestquery.inprogressrequest_onteammemberquery(userName);
				total_overdue_request_onteammember = homerequestquery.overduerequest_onteammemberquery(userName);
				total_delay_request_onteammember = homerequestquery.delayrequest_onteammemberquery(userName);
				total_ontarget_request_onteammember = homerequestquery.ontargetrequest_onteammemberquery(userName);
				total_request_onteammember = homerequestquery.totalrequest_onteammemberquery(userName);	
				complete_request_onteammember = homerequestquery.completedrequest_onteammemberquery(userName);
				total_hold_request_onteammember = homerequestquery.holdrequest_onteammemberquery(userName);
				total_close_request_onteammember = homerequestquery.closerequest_onteammemberquery(userName);
				total_cancel_request_onteammember = homerequestquery.cancelrequest_onteammemberquery(userName);		
				total_accept_request_onteammember = homerequestquery.emptyduedate_request_onteammemberquery(userName);	
				total_return_request_onteammember = homerequestquery.return_request_onteammemberquery(userName);	
				
				total_pending_request_onyou = homerequestquery.pendingrequest_onyouquery(userName);
				total_inprogress_request_onyou = homerequestquery.inprogressrequest_onyouquery(userName);
				total_overdue_request_onyou = homerequestquery.overduerequest_onyouquery(userName);
				total_delay_request_onyou = homerequestquery.delayrequest_onyourquery(userName);
				total_ontarget_request_onyou = homerequestquery.ontargetrequest_onyouquery(userName);
				total_request_onyou = homerequestquery.totalrequest_onyouquery(userName);
				complete_request_onyou = homerequestquery.completedrequestrequest_onyouquery(userName);
				total_hold_request_onyou = homerequestquery.holdrequest_onyouquery(userName);
				total_close_request_onyou = homerequestquery.closerequest_onyouquery(userName);
				total_cancele_request_onyou = homerequestquery.cancelrequest_onyouquery(userName);		
				total_accept_request_onyou = homerequestquery.emptyduedate_request_onyouquery(userName);	
				total_return_request_onyou = homerequestquery.return_request_onyouquery(userName);
				
				homeVo = new HomeVo();
				
				if(total_pending_request_onteammember != null){
				homeVo.setPendingrequest_onteammember(total_pending_request_onteammember);
				}
				
				if(total_inprogress_request_onteammember != null){
					homeVo.setInprogressrequest_onteammember(total_inprogress_request_onteammember);
					}
				
				if(total_overdue_request_onteammember != null){
					homeVo.setOverduerequest_onteammember(total_overdue_request_onteammember);
					}
				
				if(total_delay_request_onteammember != null){
					homeVo.setDelayrequest_onteammember(total_delay_request_onteammember);
					}
				
				
				if(total_ontarget_request_onteammember != null){
					homeVo.setOntargetrequest_onteammember(total_ontarget_request_onteammember);
					}
				
				if(complete_request_onteammember != null){
					homeVo.setCompletedrequest_onteammember(complete_request_onteammember);
					}
				
				
				
				if(total_hold_request_onteammember != null){
					homeVo.setHoldrequest_onteammember(total_hold_request_onteammember);
					}
				
				if(total_close_request_onteammember != null){
					homeVo.setCloserequest_onteammember(total_close_request_onteammember);
					}
				
				if(total_cancel_request_onteammember != null){
					homeVo.setCancelrequest_onteammember(total_cancel_request_onteammember);
					}
				
				if(total_accept_request_onteammember != null){
					homeVo.setAcceptrequest_onteammember(total_accept_request_onteammember);
					}
				
				
				if(total_return_request_onteammember != null){
					homeVo.setReturnrequest_onteammember(total_return_request_onteammember);
					}
				
				
				
				if(total_pending_request_onyou != null){
					homeVo.setPendingrequest_onyou(total_pending_request_onyou);
					}
				
				if(total_inprogress_request_onyou != null){
					homeVo.setInprogressrequest_onyou(total_inprogress_request_onyou);
					}				
				
				if(total_overdue_request_onyou != null){
					homeVo.setOverduerequest_onyou(total_overdue_request_onyou);
					}
				
				if(total_delay_request_onyou != null){
					homeVo.setDelayrequest_onyou(total_delay_request_onyou);
					}
				
				
				if(total_ontarget_request_onyou != null){
					homeVo.setOntargetrequest_onyou(total_ontarget_request_onyou);
					}
				
				if(total_request_onyou != null){
					homeVo.setTotalrequest_onyou(total_request_onyou);
					}
				
				if(total_request_onteammember != null){
					homeVo.setTotalrequest_onteammember(total_request_onteammember);
					}
				
				if(complete_request_onyou != null){
					homeVo.setCompletedrequest_onyou(complete_request_onyou);
					}
				
				

				if(total_hold_request_onyou  != null){
					homeVo.setHoldrequest_onyou(total_hold_request_onyou);
					}
				
				if(total_close_request_onyou != null){
					homeVo.setCloserequest_onyou(total_close_request_onyou);
					}
				
				if(total_cancele_request_onyou != null){
					homeVo.setCancelrequest_onyou(total_cancele_request_onyou);
					}
				
				
				if(total_accept_request_onyou != null){
					homeVo.setAcceptrequest_onyou(total_accept_request_onyou);
					}
				
				if(total_return_request_onyou != null){
					homeVo.setReturnrequest_onyou(total_return_request_onyou);
					}
				
				requestinhomeList.add(homeVo);
				
				

				
			} catch (Exception e) {

				e.printStackTrace();
				
			} 

			return requestinhomeList;
		}
		
	

}
