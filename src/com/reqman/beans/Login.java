package com.reqman.beans;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.reqman.dao.UserDetailsInterface;
import com.reqman.daoimpl.UserDetailsImpl;
import com.reqman.util.SessionUtils;

@ManagedBean(name = "login", eager = true)
@SessionScoped
@RequestScoped
public class Login implements Serializable {

	private static final long serialVersionUID = 1094801825228386363L;
	
	private String pwd;
	private String msg;
	private String user;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	//validate login
	public String validateUsernamePassword() {
		UserDetailsInterface userImpl = new UserDetailsImpl();
		int result = 0;
		
		try{
			result = userImpl.validate(user, pwd);
			//boolean valid = LoginDAO.validate(user, pwd);
			if (result == 1) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("username", user);
				
				return "home";
			} 
			
			
			
			
			else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Incorrect Username and Passowrd",
								"Please enter correct username and Password"));
				return "login";
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			
			return "login";
			
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";

	}
	
	public StreamedContent getImageFromDB() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		UserDetailsInterface userImpl = new UserDetailsImpl();
		HttpSession session = SessionUtils.getSession();
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
				image = userImpl.getImageDetails(user);
				if(image == null)
				{
					//image = new byte[10];
					
					//ByteArrayOutputStream bos = new ByteArrayOutputStream();
					BufferedImage img = ImageIO.read(context.getExternalContext()
							.getResourceAsStream("/resource/image/22.jpg"));
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
}
