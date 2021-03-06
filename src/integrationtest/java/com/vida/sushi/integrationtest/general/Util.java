package com.vida.sushi.integrationtest.general;

import com.elipcero.springtest.security.Oauth2TestUtil;
import com.vida.sushi.configuration.OAuth2AuthorizationServerTestIntegrationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

public class Util {

	public static HttpHeaders getHeaderForMobileUserClientCredentials(MockMvc mockMvc) throws Exception {
		return Oauth2TestUtil.getHeaderForClientCredentials(
				mockMvc,
				OAuth2AuthorizationServerTestIntegrationConfiguration.OAUTH_MOBILE_USER_CLIENTID,
				OAuth2AuthorizationServerTestIntegrationConfiguration.OAUTH_MOBILE_SECRET);
	}
}
