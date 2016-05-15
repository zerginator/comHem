package se.comhem.web.test.services;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import se.comhem.web.test.model.Hero;
import se.comhem.web.test.repositories.HeroRepository;

@Service
public class HeroServiceImpl implements HeroService{

	private static Logger LOGGER = LogManager.getLogger(HeroService.class);
    @Autowired
	private HeroRepository fileBasedRepository;
    
    @Autowired
   	private HeroRepository inMemoryRepository;
    
    @Value("${baseRepository:inmemory}")
    private String storage;
    
    @PostConstruct
    private void getRepositoryChoice() {
    	LOGGER.info("Using "+((storage.equals("file"))? "File based": " InMemory")+" Repository");
    	
    }
    
    public Map<Integer, Hero> getList() {
    		return getRepository().list();
    }

    public Hero getHero(Integer id) {
        return getRepository().get(id);
    }
    
    public void save(Hero hero) {
    	getRepository().save(hero);
    }
	
	protected HeroRepository getRepository() {
		return ((storage.equals("file"))? fileBasedRepository : inMemoryRepository);
	}
}
