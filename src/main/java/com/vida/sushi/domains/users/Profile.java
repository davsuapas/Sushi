package com.vida.sushi.domains.users;

import com.vida.sushi.domains.aquariums.Aquarium;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collections;
import java.util.List;

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
@Document
public class Profile {
	
	public Profile(String userName) {
		this.userName = userName;
		this.aquariums = Collections.emptyList();
	}
	
	@Id
	private String id;

	@Indexed
	@Field("un")
	private String userName;
	
	@Field("dai")
	private String defaultAquariumId;
	
	@Field("a")
	private List<Aquarium> aquariums;
}
