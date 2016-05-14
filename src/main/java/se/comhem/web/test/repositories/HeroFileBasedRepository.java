package se.comhem.web.test.repositories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import se.comhem.web.test.model.Hero;
import se.comhem.web.test.model.MarvelHero;

@Repository("fileBasedRepository")
public class HeroFileBasedRepository implements HeroRepository{

	private Logger LOGGER = LogManager.getLogger(HeroFileBasedRepository.class);
	
	private static String filename = System.getProperty("user.home")+File.separator+"Hero.db";
	
    public Map<Integer, Hero> list() {
    	return getHeroes();
    }

    public Hero get(Integer id) {
    	Map<Integer,Hero> heroList = getHeroes();
    	if(heroList != null) return heroList.get(id);
    	return null;
    }

    public void save(Hero hero) {
    	Map<Integer,Hero> heroList = getHeroes();
    	if(!heroList.containsValue(hero)){
    		heroList.put(heroList.size(), hero);
	    	String json = new Gson().toJson(heroList);
	    	storeHeroesIntoFile(json);
    	}
    }
    
    private Map<Integer,Hero> getHeroes() {
    	Gson gson = new Gson();
    	Type type = new TypeToken<Map<Integer, MarvelHero>>(){}.getType();
    	Map<Integer,Hero> map = gson.fromJson(loadHeroesFromFile(), type);
		if(map == null) map =  new HashMap<Integer, Hero>();
		return map;
    }
    
    private String loadHeroesFromFile() {
    	FileInputStream fis;
    	StringBuilder sb = new StringBuilder();
		try {
    		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(filename)));
    		BufferedReader bufferedReader = new BufferedReader(isr);
    		String line;
    		while ((line = bufferedReader.readLine()) != null) {
    			sb.append(line);
    		}
		} catch (IOException e) {
			LOGGER.error("Could not read File : ",e);
		}
		return sb.toString();
    }
    
    private void storeHeroesIntoFile(String json) {
    	FileOutputStream outputStream;
    	try {
    	  outputStream = new FileOutputStream(filename, true);
    	  outputStream.write(json.getBytes());
    	  outputStream.close();
    	} catch (Exception e) {
    	  e.printStackTrace();
    	}
    }
}
