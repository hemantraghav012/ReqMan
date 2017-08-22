package com.reqman.validator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
@FacesValidator("textValidator")
public class TextValidator implements Validator
{

	private String textName;
	public TextValidator(){
		
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}
	
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object obj)
			throws ValidatorException {

		String model = (String) obj;
		

		if (model.length() > 50) {
			FacesMessage msg = new FacesMessage(
					" Maximum Length of 50 is exceeded.Please enter values within range");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(msg);
		}

	}

}
