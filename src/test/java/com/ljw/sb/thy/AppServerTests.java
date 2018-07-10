package com.ljw.sb.thy;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ljw.springboot.thy.AppServer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for {@link SampleWebThymeleaf3Application}.
 *
 * @author Dave Syer
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes=AppServer.class)
@DirtiesContext
public class AppServerTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void testHome() throws Exception {
		System.out.println(port);
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("<title>Messages");
		assertThat(entity.getBody()).doesNotContain("layout:fragment");
	}

	@Test
	public void testCreate() throws Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.set("text", "FOO text");
		map.set("summary", "FOO");
		URI location = this.restTemplate.postForLocation("/", map);
		assertThat(location.toString()).contains("localhost:" + this.port);
	}

	@Test
	public void testCss() throws Exception {
		ResponseEntity<String> entity = this.restTemplate
				.getForEntity("/css/bootstrap.min.css", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("body");
	}

}