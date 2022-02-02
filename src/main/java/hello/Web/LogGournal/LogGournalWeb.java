package hello.Web.LogGournal;

import java.util.Date;


public class LogGournalWeb {
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public LogGournalWeb(Long id, String sdate, String deviceName, String userName,
			String source, String recordsAffected) {
		super();
		this.id = id;
		this.sdate = sdate;
		this.deviceName = deviceName;
		this.userName = userName;
		this.source = source;
		this.recordsAffected = recordsAffected;
	}
	public Long id;
	public Date dt;
	public String  sdate;
	public String deviceName ;
	public String deviceCode ;
	public String userCode ;
	public String userName ;
	public String source ;
	public String recordsAffected ;
}
