package hello.Client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientReq {

		public ClientReq(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
		public ClientReq(String id, String name, String email, String phone) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
			this.phone = phone;
		}
		
		@JsonProperty("id")
		public String id;
		@JsonProperty("name")
		String name;
		@JsonProperty("email")
		public String email;
		@JsonProperty("phone")
		public String phone;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public void setName(String name) {
			this.name = name;
		}
	

}
