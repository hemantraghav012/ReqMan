import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;




@ManagedBean(name="bean",eager = true)
@RequestScoped
@ViewScoped
public class bean {
	 private StreamedContent file;
     
	    public bean() {        
	        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resource/mystyle/mystyle.css");
	        file = new DefaultStreamedContent(stream, "image/jpg", "Attachment");
	    }
	 
	    public StreamedContent getFile() {
	        return file;
	    }
}
