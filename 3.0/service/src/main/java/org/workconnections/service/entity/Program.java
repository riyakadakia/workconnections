package org.workconnections.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Program {
	
	@Id
	private String programId;
	
}
