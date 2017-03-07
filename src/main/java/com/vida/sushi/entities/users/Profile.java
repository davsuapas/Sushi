package com.vida.sushi.entities.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

/**
 * @author dsuarez
 * 
 * Profile entity
 * Embedded aquarium information
 *
 */
@Data
@TypeAlias("pfe")
@CompoundIndex(name = "p_un", unique = true, def = "{'p' : 1, 'un' : 1}")
@Document
public class Profile {
	
	public Profile(String provider, String userName) {
		this.provider = provider;
		this.userName = userName;
	}
	
	@Id
	private String id;

	@Field("p")
	private String provider;
	
	@Field("un")
	private String userName;
}
