package org.workconnections.service.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Id
	private String id;
	private String name;
	private Date created= new Date();
	private String email;
	private String phone;
	

	public Integer getId() throws Exception {
		Integer userId = Integer.valueOf(-1);
		if (this.id != null) {
			userId = Integer.valueOf(this.id);
		}
		return userId;
	}
	
	public void setId(Integer id) {
		this.id = String.valueOf(id);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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
}