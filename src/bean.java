import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;






import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.PieChartModel;
 
@ManagedBean
public class bean implements Serializable {
 
    private PieChartModel pieModel1;
  
 
    @PostConstruct
    public void init() {
        createPieModels();
    }    
   
    
 
    private void createPieModels() {
        pieModel1 = new PieChartModel();
         
        pieModel1.set("Brand 1", 540);
        pieModel1.set("Brand 2", 325);
        pieModel1.set("Brand 3", 702);
        pieModel1.set("Brand 4", 421);
         
        pieModel1.setTitle("Simple Pie");
        pieModel1.setLegendPosition("w");
    }



	public PieChartModel getPieModel1() {
		return pieModel1;
	}



	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}
     
   
     
}