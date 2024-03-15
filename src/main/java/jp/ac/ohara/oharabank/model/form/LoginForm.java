package jp.ac.ohara.oharabank.model.form;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoginForm {
		
		@Column(name="user_name",length=255)
		private String userName;
		
		@Column(name="password")
		private String password;

	
	}

 