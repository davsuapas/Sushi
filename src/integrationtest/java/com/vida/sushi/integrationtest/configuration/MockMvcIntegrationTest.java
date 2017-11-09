package com.vida.sushi.integrationtest.configuration;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class MockMvcIntegrationTest {
	
	public MockMvcIntegrationTest() {
		mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module()); // optional serialize
		mapper.registerModule(new JavaTimeModule() );
	}
	
	// Important for applying security options
	@Autowired
    private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private WebApplicationContext context;
	
	protected ObjectMapper mapper;
		
	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.addFilter(springSecurityFilterChain).build();
	}
}
