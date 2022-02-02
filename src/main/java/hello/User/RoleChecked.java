package hello.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleChecked extends Role {

	public RoleChecked(boolean checked) {
		super();
		this.checked = checked;
	}
	public RoleChecked(Role r, boolean checked) {
		super(r.getId(), r.getName());
		this.checked = checked;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 2918274105272651602L;
	@JsonProperty("checked")
	boolean checked;
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
