package com.reqman.beans;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.reqman.dao.AccountInterface;
import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.AccountImpl;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.AccountVo;
import com.reqman.vo.AdminVo;
import com.reqman.vo.NewrequestVo;




@ManagedBean(name="accountbean",eager = true)
@RequestScoped
@ViewScoped
@SessionScoped
@javax.faces.view.ViewScoped
public class Accountbean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6211763112586779685L;
	private AccountInterface accountInterface = new AccountImpl();
	 private List<AccountVo> accountList = new ArrayList<AccountVo>();	
	 private List<AccountVo> filteredaccountList = new ArrayList<AccountVo>();
	 private UploadedFile logo;
		private Integer id;
		private AccountVo accountVo = new AccountVo();
		private  String accountname;
		private boolean status;
	 private String imagename;
	 private String organizationkey;
		@PostConstruct
	    public void init() 
		{
			try
			{
				 	
				System.out.println("--Account-->");
				accountList = new  ArrayList<AccountVo>();			
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				System.out.println("--usersession--userName-->"+userName);
				accountList = accountInterface.getaccountDetails(userName.toLowerCase().trim());
				
				setFilteredaccountList(accountList);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

		
		  
		  
		public String modifyAction() throws IOException {
			
	        try
	        {
	        	HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");				
				System.out.println("--usersession--userName-->"+userName);
				
				
	        	//setId(id);
	        	accountVo = accountInterface.getAccountById(id,userName.toLowerCase().trim());      	

	        	System.out.println("modify action"+id);
	        	
	        		setAccountname(accountVo.getName() != null ? accountVo.getName(): "");
	        		setImagename(accountVo.getImagename() != null ? accountVo.getImagename() : "");
	        		setOrganizationkey(accountVo.getOrganizationkey() !=null ? accountVo.getOrganizationkey(): "");

	        }
	        catch(Exception e){
	        	e.printStackTrace();
	        	
	        }
	        return "modifyaccount.xhtml";
	    }
		
		
		
		public void validateFile(FacesContext ctx,
	            UIComponent comp,
	            Object value) {
	        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
	        UploadedFile file = (UploadedFile)value;
	        int fileByte = file.getContents().length;
	        if(fileByte != 0){
	        if(fileByte > 5000000){
	            msgs.add(new FacesMessage("Too big must be at most 5MB"));
	        }
	        if (!(file.getContentType().startsWith("image"))) {
	            msgs.add(new FacesMessage("not an Image file"));
	        }
	        if (!msgs.isEmpty()) {
	            throw new ValidatorException(msgs);
	        }
	        }
	        else{
	        	
	        }
	    }
		
		
		
		
		public String updateUserdetail()
		{
			int result = 0;
			try{
				System.out.println("--updateRequest-status-"+accountname);
				System.out.println("--updateRequest-status-"+status);
				System.out.println("--updateRequest-status-"+id);
				HttpSession session = SessionUtils.getSession();
				String userName = (String)session.getAttribute("username");
				
				System.out.println("--usersession--userName-->"+userName);
				
	        
	        	result = accountInterface.updatelogo(accountname,status,id,userName.toLowerCase().trim(),logo,imagename);
	        	
	        	
	        	if (result == 1) {
	        		//For App admin
					return "home";
					
				} 
				
				else if (result == 2) {
					//for Account Admin
					return "home";
					
				} 
				
				else if (result ==3) {
					//For Requestor
					return "home";
					
				} 
				
				else if (result == 4) {
					//For Team member
					return "home";
					
				} 
	        	
	        	
	        	else
	        	{
	        		FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Problem while modifying the Category",
									""));
					return "modifyaccount.xhtml";
	        	}
	        	
	        	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Problem while modifying the Category",
								""));
				return "modifyaccount.xhtml";
			}
			
		}
		
		
	 
		@SuppressWarnings("unused")
		public StreamedContent getLogoFromDB() throws IOException {
			FacesContext context = FacesContext.getCurrentInstance();
			UserDetailsInterface userImpl = new UserDetailsImpl();
			HttpSession session = SessionUtils.getSession();
			String userName = (String)session.getAttribute("username");
			
			String userLoginId = "";
			byte[] image = null;
			if (session == null) {
				return new DefaultStreamedContent();
			} else {
				//user = (String)session.getAttribute("username");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();

				// Reading image from database assuming that product image (bytes)
				// of product id I1 which is already stored in the database.
				try {
				
					
					if(userName  == null)
					{
						userName  = (String)session.getAttribute("username");
					}
					image = accountInterface.getImageDetails(userName.toLowerCase().trim());
					if(image == null)
					{
						//image = new byte[10];
						
						//ByteArrayOutputStream bos = new ByteArrayOutputStream();
						BufferedImage img = ImageIO.read(context.getExternalContext()
								.getResourceAsStream("/resource/image/C8 logo.jpeg"));
						int w = img.getWidth(null);
						int h = img.getHeight(null);
			 
						// image is scaled two times at run time
						int scale = 2;
			 
						BufferedImage bi = new BufferedImage(w * scale, h * scale,
								BufferedImage.TYPE_INT_ARGB);
						Graphics g = bi.getGraphics();
			 
						g.drawImage(img, 10, 10, w * scale, h * scale, null);
			 
						ImageIO.write(bi, "png", bos);
			 
						return new DefaultStreamedContent(new ByteArrayInputStream(
								bos.toByteArray()), "image/png");
					}
				} catch (Exception e) { 
						e.printStackTrace();
				}

				return new DefaultStreamedContent(new ByteArrayInputStream(image),
						"image/png");

			}
		}

		
		
	 
	 
	public List<AccountVo> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<AccountVo> accountList) {
		this.accountList = accountList;
	}
	public List<AccountVo> getFilteredaccountList() {
		return filteredaccountList;
	}
	public void setFilteredaccountList(List<AccountVo> filteredaccountList) {
		this.filteredaccountList = filteredaccountList;
	}








	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public UploadedFile getLogo() {
		return logo;
	}





	public void setLogo(UploadedFile logo) {
		this.logo = logo;
	}




	public AccountVo getAccountVo() {
		return accountVo;
	}




	public void setAccountVo(AccountVo accountVo) {
		this.accountVo = accountVo;
	}




	public String getAccountname() {
		return accountname;
	}




	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}




	public boolean isStatus() {
		return status;
	}




	public void setStatus(boolean status) {
		this.status = status;
	}




	public String getImagename() {
		return imagename;
	}




	public void setImagename(String imagename) {
		this.imagename = imagename;
	}




	public String getOrganizationkey() {
		return organizationkey;
	}




	public void setOrganizationkey(String organizationkey) {
		this.organizationkey = organizationkey;
	}




	 
}
