package se.comhem.web.test.repositories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import se.comhem.web.test.model.Hero;
import se.comhem.web.test.model.MarvelHero;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Repository("fileBasedRepository")
public class HeroFileBasedRepository implements HeroRepository{

	private Logger LOGGER = LogManager.getLogger(HeroFileBasedRepository.class);
	@Value("${fileNameDB:Hero.db}")
	private  String filename;
	private  String pathDb;
	
	@PostConstruct
	public void setup() {
		pathDb = System.getProperty("user.home")+File.separator+filename;
	}
	
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
    	if(!isHeroExisting(heroList,hero)){
    		heroList.put(heroList.size(), hero);
	    	String json = new Gson().toJson(heroList);
	    	storeHeroesIntoFile(json);
    	}
    }
    
    private boolean isHeroExisting(Map<Integer,Hero> heroList,Hero hero) {
		boolean heroExists = false;
    	for( Entry<Integer, Hero> entry: heroList.entrySet()) {
			if (entry.getValue().getName().equals(hero.getName())) {
				heroExists = true;
			}
		}
    	return heroExists;
	}

	private Map<Integer,Hero> getHeroes() {
    	Gson gson = new Gson();
    	Type type = new TypeToken<Map<Integer, MarvelHero>>(){}.getType();
    	Map<Integer,Hero> map = gson.fromJson(loadHeroesFromFile(), type);
		if(map == null) map =  new HashMap<Integer, Hero>();
		return map;
    }
    
    @SuppressWarnings("resource")
	private String loadHeroesFromFile() {
    	StringBuilder sb = new StringBuilder();
    	File filedb = new File(pathDb);
    	
			
		try {
			if(!filedb.exists()) filedb.createNewFile();
    		InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(pathDb)));
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
    	  outputStream = new FileOutputStream(pathDb,false);
    	  outputStream.write(json.getBytes());
    	  outputStream.close();
    	} catch (Exception e) {
    		LOGGER.error("Could not write to File : ",e);
    	}
    }
}
