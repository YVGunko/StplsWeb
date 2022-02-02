package hello.OutDoorOrder;

public class OutDoorOrderListRep {
	
	public OutDoorOrderListRep(String id, String sDate, String sClient, String sDivision, String sDesc, String sUser) {
		super();
		this.sClient = sClient;
		this.id = id;
		this.sDate = sDate;
		this.sDivision = sDivision;
		this.sDesc = sDesc;
		this.sUser = sUser;
	}

	public String sClient;
	public String id;	
	public String sDate ;	
	public String sDivision ;	
	public String sDesc ;	
	public String sUser ;

	public OutDoorOrderListRep() {
		super();
	}
}
