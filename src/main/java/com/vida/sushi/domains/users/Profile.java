package com.vida.sushi.domains.users;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.vida.sushi.domains.aquariums.Aquarium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Profile domain
 * Embedded aquarium information
 *
 * @author dav.sua.pas@gmail.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
	
	@Field("dai")
	private String defaultAquariumId;
	
	@Field("a")
	private List<Aquarium> aquariums;
}
