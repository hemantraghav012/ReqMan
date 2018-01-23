package com.reqman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CellEditEvent;


import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.reqman.dao.AccountInterface;
import com.reqman.dao.UserroleInterface;
import com.reqman.daoimpl.AccountImpl;
import com.reqman.daoimpl.UserroleImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AccountVo;
import com.reqman.vo.UserroleVo;


@ManagedBean(name="userrolebean",eager = true)
@ViewScoped
public class Userrolebean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8915459114992031617L;
	
	private UserroleInterface userroleInterface = new UserroleImpl();
	 private List<UserroleVo> userroleList = new ArrayList<UserroleVo>();	
	 private List<UserroleVo> filtereduserroleList = new ArrayList<UserroleVo>();
	 private LineChartModel lineModel;
	 
	 

		@PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--Userrole-->");
				 userroleList = new  ArrayList<UserroleVo>();			
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			System.out.println("--usersession--userName-->"+userName);
				 userroleList = userroleInterface.getuserroleDetails(userName);
				
				setFiltereduserroleList(userroleList);
				 createLineModels();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

	 
		private void createLineModels() {
			// TODO Auto-generated method stub
			
			 lineModel = initCategoryModel();
		        lineModel.setTitle("Category Chart");
		        lineModel.setLegendPosition("e");
		        lineModel.setShowPointLabels(true);
		        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
		        Axis yAxis = lineModel.getAxis(AxisType.Y);
		        yAxis.setLabel("User Roles");
		        yAxis.setMin(0);
		        
		}


		@SuppressWarnings({ "unchecked", "rawtypes" })
		
		 
		
		private LineChartModel initCategoryModel() {
			
			Map<String,Double> userrolelinemap = new HashMap<String,Double>();
			
			 try 
			  {	       
			    HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");			
				userrolelinemap = userroleInterface.linechart(userName );
			  } catch (Exception e)
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }			

			
			
			
	        LineChartModel model = new LineChartModel();
	 
	        ChartSeries chartseries1 = new ChartSeries();

            for(Map.Entry m:userrolelinemap.entrySet())
            {  
        	  String requestnumber= (String) m.getKey();
        	  Double friendemailid= (Double) m.getValue();	
        	  
        	   chartseries1.set(requestnumber,friendemailid);
          }
            
          model.addSeries(chartseries1);
          
      return model;
      
	}
	 
		
		

		public void onCellEdit(CellEditEvent event) {
		     int result = 0;
		     String oldValue = "";
		     String newValue = "";
		     Integer updateUserroleid = 0;
			 try
			 {
				 oldValue = (String)event.getOldValue();
			     newValue = (String)event.getNewValue();
			     updateUserroleid = (Integer) event.getComponent().getAttributes().get("updateUserroleid");
		         System.out.println("updateUserroleid"+updateUserroleid);
		        if(newValue != null && !newValue.equals(oldValue)) 
		        {
		        	result = userroleInterface.updateUserrole(oldValue, newValue, updateUserroleid);
		            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
		            FacesContext.getCurrentInstance().addMessage(null, msg);
		        }
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
				 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error", e.getMessage().toString());
		            FacesContext.getCurrentInstance().addMessage(null, msg);
			 }
		       
		    }
		
		
	 
	 
	 
	 
	public List<UserroleVo> getUserroleList() {
		return userroleList;
	}
	public void setUserroleList(List<UserroleVo> userroleList) {
		this.userroleList = userroleList;
	}
	public List<UserroleVo> getFiltereduserroleList() {
		return filtereduserroleList;
	}
	public void setFiltereduserroleList(List<UserroleVo> filtereduserroleList) {
		this.filtereduserroleList = filtereduserroleList;
	}


	public LineChartModel getLineModel() {
		return lineModel;
	}


	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
	 
	 
	 
	 
	 
	 
	
	
}
