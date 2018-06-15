package com.reqman.util;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

@FacesValidator(value = "dateRangeValidator")
public class DateRangeValidator implements Validator {
	@Override
	public void validate(FacesContext context, UIComponent uiComponent, Object o) throws ValidatorException {
		UIInput startDateInput = (UIInput) uiComponent.getAttributes().get("startDateAttr");
		Date startDate = (Date) startDateInput.getValue();
		Date endDate = (Date) o;

		if (endDate.before(startDate)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid start/end dates.",
					"Start date cannot be after end date.");
			throw new ValidatorException(msg);
		} else {
			RequestContext c = RequestContext.getCurrentInstance();
			c.execute("PF('dateRangeDlg').hide();");
			c.execute("location.reload();");
		}
	}
}
