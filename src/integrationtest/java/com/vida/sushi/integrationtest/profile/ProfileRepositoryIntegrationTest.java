package com.vida.sushi.integrationtest.profile;

import com.elipcero.springdatarest.webmvc.RepositoryRestMvcHeader;
import com.vida.sushi.domains.aquariums.Aquarium;
import com.vida.sushi.domains.aquariums.Aquarium.AquariumType;
import com.vida.sushi.domains.users.Profile;
import com.vida.sushi.integrationtest.configuration.MockMvcIntegrationTest;
import com.vida.sushi.integrationtest.general.Util;
import com.vida.sushi.repositories.users.ProfileRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
public class ProfileRepositoryIntegrationTest extends MockMvcIntegrationTest {

	private static final String AQUARIUM_NAME = "aquariumName";

	@Autowired
	private ProfileRepository profileRepository;

	@Test
	public void authentication_GetProfileById_ShouldReturnErrorUnauthorized() throws Exception {
		mockMvc.perform(get("/profiles/{id}", 1))
				.andExpect(status().isUnauthorized());
	}

	@Test
	public void profile_GetProfileById_ShouldReturnProfileAndAquarium() throws Exception {

		Profile profile = saveProfile();
		Aquarium aquarium = profile.getAquariums().stream().findFirst().get();

		mockMvc.perform(get("/profiles/{id}", profile.getId())
				.headers(Util.getHeaderForMobileUserClientCredentials(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName", is(profile.getUserName())))
                .andExpect(jsonPath("$.defaultAquariumId", is(profile.getDefaultAquariumId())))
                .andExpect(jsonPath("$.aquariums[0].id", is(aquarium.getId())))
                .andExpect(jsonPath("$.aquariums[0].name", is(aquarium.getName())))
                .andExpect(jsonPath("$.aquariums[0].capacity", is((double)aquarium.getCapacity())))
                .andExpect(jsonPath("$.aquariums[0].deep", is((double)aquarium.getDeep())))
                .andExpect(jsonPath("$.aquariums[0].high", is((double)aquarium.getHigh())))
                .andExpect(jsonPath("$.aquariums[0].type", is(aquarium.getType().toString())))
				.andExpect(jsonPath("$.aquariums.length()", is(profile.getAquariums().size())));

		deleteProfile(profile);
	}

	@Test
	public void profile_UpdateSomePropertiesProfile_ShouldUpdateAndReturnNonContent() throws Exception {

		Profile profile = saveProfile();

		Profile profileForUpdating = Profile.builder()
				.id(profile.getId())
				.defaultAquariumId(ObjectId.get().toString()).build();

		mockMvc.perform(patch("/profiles/{id}/updatenonull", profile.getId())
				.content(mapper.writeValueAsString(profileForUpdating))
				.headers(Util.getHeaderForMobileUserClientCredentials(mockMvc))
				.header(RepositoryRestMvcHeader.updateNoMerge, ""))
                .andExpect(status().isNoContent());

		Profile profileUpdated = profileRepository.findOne(profile.getId());

		assertThat(profileUpdated.getDefaultAquariumId()).isEqualTo(profileForUpdating.getDefaultAquariumId());

		deleteProfile(profile);
	}

	@Test
	public void aquarium_InsertAquariumIntoProfile_ShouldInsertAndReturnNonContent() throws Exception {

		Profile profile = saveProfile();

		String aquariumIdForInserting = ObjectId.get().toString();
		Profile aquariumForInserting = Profile.builder()
				.id(profile.getId())
				.aquariums(
						Collections.singletonList(Aquarium.builder()
							.id(aquariumIdForInserting)
							.name(AQUARIUM_NAME)
							.capacity(1)
							.creationDate(LocalDateTime.now())
							.deep(1)
							.high(1)
							.type(AquariumType.saltWater).build())).build();

		mockMvc.perform(patch("/profiles/{id}/mergeembedded/aquariums", profile.getId())
				.content(mapper.writeValueAsString(aquariumForInserting))
				.headers(Util.getHeaderForMobileUserClientCredentials(mockMvc))
				.header(RepositoryRestMvcHeader.updateNoMerge, ""))
                .andExpect(status().isNoContent());

		Aquarium aquariumInserted = profileRepository.findOne(
				profile.getId()).getAquariums().stream().filter(
						a -> a.getId().equals(aquariumIdForInserting)).findFirst().get();

		assertThat(aquariumInserted.getName()).isEqualTo(AQUARIUM_NAME);

		deleteProfile(profile);
	}

	@Test
	public void aquarium_UpdateAquariumIntoProfile_ShouldUpdateAndReturnNonContent() throws Exception {

		final String nameAquariumUpdated = "nameAquariumUpdated";

		Profile profile = saveProfile();
		profile.getAquariums().stream().findFirst().get().setName(nameAquariumUpdated);

		mockMvc.perform(patch("/profiles/{id}/mergeembedded/aquariums", profile.getId())
				.content(mapper.writeValueAsString(profile))
				.headers(Util.getHeaderForMobileUserClientCredentials(mockMvc))
				.header(RepositoryRestMvcHeader.updateNoMerge, ""))
                .andExpect(status().isNoContent());

		Aquarium aquariumUpdated = profileRepository.findOne(
				profile.getId()).getAquariums().stream().findFirst().get();

		assertThat(aquariumUpdated.getName()).isEqualTo(nameAquariumUpdated);

		deleteProfile(profile);
	}

	private void deleteProfile(Profile profile) {
		profileRepository.delete(profile);
	}

	private Profile saveProfile() {
		return profileRepository.save(createProfile());
	}

	private Profile createProfile() {

		String aquariumId = ObjectId.get().toString();

		return Profile.builder()
			.userName(ObjectId.get().toString())
			.defaultAquariumId(aquariumId)
			.aquariums(
					Collections.singletonList(Aquarium.builder()
						.id(aquariumId)
						.name(AQUARIUM_NAME)
						.capacity(1)
						.creationDate(LocalDateTime.now())
						.deep(1)
						.high(1)
						.type(AquariumType.saltWater).build())).build();
	}
}
