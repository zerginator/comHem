package se.comhem.web.test.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import se.comhem.web.test.AppConfig;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:application.properties")
@ContextConfiguration(classes = AppConfig.class)
public class HeroServiceTest {

	@Autowired
	HeroServiceImpl heroService;
	
	@Test
	public void testThatSelectingFileRepositoryWorks() {
		Assert.assertEquals("se.comhem.web.test.repositories.HeroFileBasedRepository", heroService.getRepository().getClass().getCanonicalName());
	}
}
