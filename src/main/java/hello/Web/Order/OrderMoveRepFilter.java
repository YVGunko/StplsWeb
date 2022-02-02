package hello.Web.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderMoveRepFilter {
    
    private Date dateS = new Date();
    private Date dateE = new Date();

    public String getDateS() {
    		return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateS);
    }

    public void setDateS(String dateS) {
	    	try {
	    		this.dateS = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(dateS);
	    	}
	    	catch(Exception e) {
	    	}
    }
    
    public String getDateE() {
		return new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(dateE);
    }

	public void setDateE(String dateE) {
	    	try {
	    		this.dateE = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(dateE);
	    	}
	    	catch(Exception e) {
	    	}
	}
}
