package com.niqdev.mono;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.niqdev.mono.config.IntegrationTestConfig;

@SpringBootTest
@ActiveProfiles("test")
@Import(IntegrationTestConfig.class)
class MonoNiqDevApplicationTests {

	@Test
	void contextLoads() {
	}

}
