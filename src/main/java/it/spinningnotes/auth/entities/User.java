package it.spinningnotes.auth.entities;
import java.util.Date;

import javax.validation.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import it.spinningnotes.auth.validators.ValidPassword;
import lombok.Data;

@Data
public class User {
	@Id
	private String id;
	private String name;
	private String lastName;
	private String user;
	@ValidPassword
	private String password;
	@Email(message = "Email is mandatory")
	private String email;
	@CreatedDate
	private Date created;
	@LastModifiedDate
	private Date lastModified;
	
}
