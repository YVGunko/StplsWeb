package hello.OutDoorOrder;

import java.util.ArrayList;
import java.util.List;

public class OutDoorOrderRep {
	
	public OutDoorOrderRep(String name, String sColor, String sLiner, String sRant, String sShpalt, String attribute,
			String number, Boolean bDet) {
		super();
		this.name = name;
		this.sColor = sColor;
		this.sLiner = sLiner;
		this.sRant = sRant;
		this.sShpalt = sShpalt;
		this.attribute = attribute;
		this.number = number;

		this.bDet = bDet;
	}

	public OutDoorOrderRep(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}

	
	public String name;	

	public String sColor ;	
	public String sLiner ;	
	public String sRant ;	
	public String sShpalt ;
	public String attribute ;
	public String number ;
	public Boolean bDet ;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getsColor() {
		return sColor;
	}
	public void setsColor(String sColor) {
		this.sColor = sColor;
	}
	public String getsLiner() {
		return sLiner;
	}
	public void setsLiner(String sLiner) {
		this.sLiner = sLiner;
	}
	public String getsRant() {
		return sRant;
	}
	public void setsRant(String sRant) {
		this.sRant = sRant;
	}
	public String getsShpalt() {
		return sShpalt;
	}
	public void setsShpalt(String sShpalt) {
		this.sShpalt = sShpalt;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public OutDoorOrderRep() {
		super();
	}
	public Boolean getbDet() {
		return bDet;
	}
	public void setbDet(Boolean bDet) {
		this.bDet = bDet;
	}


}
