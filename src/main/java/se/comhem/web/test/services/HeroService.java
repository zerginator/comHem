package se.comhem.web.test.services;

import java.util.Map;

import se.comhem.web.test.model.Hero;

public interface HeroService {

	Map<Integer, Hero> getList();
	Hero getHero(Integer id);
	void save(Hero hero);
	
}
