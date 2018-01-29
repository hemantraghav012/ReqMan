package com.reqman.beans.auto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;

import com.reqman.dao.FriendMasterInterface;
import com.reqman.daoimpl.FriendMasterImpl;
import com.reqman.util.SessionUtils;
import com.reqman.vo.UserVo;


@FacesConverter("requestFriendConverter")
public class RequestFriendConverter implements Converter
{

	private FriendMasterInterface  friendMasterInterface = new FriendMasterImpl();
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		List<UserVo> friendList = new ArrayList<UserVo>();
		if(value != null && value.trim().length() > 0) {
            try {
            	HttpSession session = SessionUtils.getSession();
        		String userName = (String) session.getAttribute("username");
        		System.out.println("--usersession--userName-->" + userName);
        		friendList = friendMasterInterface.AllUsers(userName);
                //ThemeService service = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
        		if(friendList != null && friendList.size() != 0)
        		{
        			for(UserVo userVo : friendList)
        			{
        				if(userVo != null && userVo.getUserId() != null && userVo.getUserId().toString().trim().equals(value))
        				{
        					return userVo;
        				}
        			}
        		}
        		
        		return null;
                
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            } catch (Exception e) {
				e.printStackTrace();
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error", e.getMessage()));
			}
        }
        else {
            return null;
        }
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		 if(value != null) {
	            return String.valueOf(((UserVo) value).getUserId());
	        }
	        else {
	            return null;
	        }
	}

}
