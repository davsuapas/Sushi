package com.vida.sushi.domains.aquariums;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Aquarium domain. This domain is embedded into profile,
 * because is a small list and when the profile is read, we need
 * read all aquariums domains by profile. Improves performance
 * 
 * @author dav.sua.pas@gmail.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@TypeAlias("aqm")
@Document
public class Aquarium {

	@Id
	private String id;
	
	@Field("n")
	private String name;
	
	public enum AquariumType {
		saltWater,
		freshWater
	}
	
	@Field("t")
	private AquariumType type;
	
	@Field("cd")
	private LocalDateTime creationDate;
	
	@Field("h")
	private float high;
	
	@Field("w")
	private float width;
	
	@Field("d")
	private float deep;
	
	@Field("c")
	private float capacity;

	public void setName(String value) {
		name = value;
	}
}
