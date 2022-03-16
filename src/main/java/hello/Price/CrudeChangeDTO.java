package hello.Price;

import java.util.List;

public class CrudeChangeDTO {
    public CrudeChangeDTO(List<Crude> crudes) {
		super();
		this.crudes = crudes;
	}



	public CrudeChangeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}



	private List<Crude> crudes;

    

    public void addCrude(Crude crude) {
        this.crudes.add(crude);
    }



	public List<Crude> getCrudes() {
		return crudes;
	}



	public void setCrudes(List<Crude> crudes) {
		this.crudes = crudes;
	}
	
    // getter and setter
}
