package com.vida.sushi.integrationtest.configuration;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class MockMvcIntegrationTest {
	
	// Important for applying security options
	@Autowired
    private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	@Qualifier("objectMapper")
	protected ObjectMapper mapper;
	
	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.addFilter(springSecurityFilterChain).build();
	}
}
