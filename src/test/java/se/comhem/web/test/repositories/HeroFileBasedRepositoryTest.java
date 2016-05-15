package se.comhem.web.test.repositories;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import se.comhem.web.test.AppConfig;
import se.comhem.web.test.model.Gender;
import se.comhem.web.test.model.MarvelHero;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:application.properties")
@ContextConfiguration(classes = AppConfig.class)
public class HeroFileBasedRepositoryTest {

	private static Logger LOGGER = LogManager.getLogger(HeroFileBasedRepositoryTest.class);
	@Autowired
	public HeroFileBasedRepository heroFileBasedRepository;
	
	@Value("${fileNameDB:HeroTest.db}")
	private  String filename;
	
	@Before
	public void setup() {
		File file = new File(System.getProperty("user.home")+File.separator+filename);
		if(file.exists()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				LOGGER.error("Could not create Test DB file. Do you have the right permissions for these folder ?"+System.getProperty("user.home")+File.separator+filename);
			}
		}
	}
	
	@Test
	public void testThatHeroWillBeSavedAndFileCreated() {
		heroFileBasedRepository.save(new MarvelHero("Hulk","Women",Gender.MAN));
		Assert.notNull(heroFileBasedRepository.get(0));
	}
	
	@Test
	public void testThatAHeroWithTheSameNameWillBeStoredOnlyOnce() {
		Gson gson = new Gson();
		MarvelHero hero = new MarvelHero("Hulk","Women",Gender.MAN);
		heroFileBasedRepository.save(hero);
		heroFileBasedRepository.save(hero);
		heroFileBasedRepository.save(hero);
		Assert.isTrue(heroFileBasedRepository.list().size() == 1);
		Assert.hasText(gson.toJson(heroFileBasedRepository.get(0)),gson.toJson(hero));
	}
	
	
	@Test
	public void testThatHeroesListWillBeReturned() {
		heroFileBasedRepository.save(new MarvelHero("Hulk","Women",Gender.MAN));
		Assert.notNull(heroFileBasedRepository.get(0));
	}
	
}
