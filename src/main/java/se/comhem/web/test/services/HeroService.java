package se.comhem.web.test.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.comhem.web.test.model.Hero;
import se.comhem.web.test.repositories.HeroRepository;

@Service
public class HeroService {

    @Autowired
    private HeroRepository fileBasedRepository;

    public Map<Integer, Hero> list() {
        return fileBasedRepository.list();
    }

    public Hero get(Integer id) {
        return fileBasedRepository.get(id);
    }
    
    public void save(Hero hero) {
    	fileBasedRepository.save(hero);
    }

	

}
