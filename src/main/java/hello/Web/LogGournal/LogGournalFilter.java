package hello.Web.LogGournal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import hello.Device.Device;
import hello.User.User;

public class LogGournalFilter {
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
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	private Date dateS = new Date();
	private Date dateE = new Date();
	private Device device ;
	private User user ;
	private String source ;
	
}
