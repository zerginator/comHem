package se.comhem.web.test.repositories;

import java.util.Map;

import se.comhem.web.test.model.Hero;

public interface HeroRepository {

    Map<Integer, Hero> list();
    Hero get(Integer id);
    void save(Hero hero);

}
