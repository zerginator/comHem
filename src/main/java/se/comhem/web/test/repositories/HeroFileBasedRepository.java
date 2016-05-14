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

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import se.comhem.web.test.model.Hero;
import se.comhem.web.test.model.MarvelHero;

@Repository("fileBasedRepository")
public class HeroFileBasedRepository implements HeroRepository{

	
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
    	writeToFile(hero);
    }
    
    
    private void writeToFile(Hero hero) {
    	Gson gson = new Gson();
    	Map<Integer,Hero> heroList = getHeroes();
    	if(heroList == null) heroList = new HashMap<Integer, Hero>();
    	if(!heroList.containsValue(hero)){
    		heroList.put(3, hero);
	    	String s = gson.toJson(heroList);
	    	FileOutputStream outputStream;
	    	try {
	    	  outputStream = new FileOutputStream(filename, true);
	    	  outputStream.write(s.getBytes());
	    	  outputStream.close();
	    	} catch (Exception e) {
	    	  e.printStackTrace();
	    	}
    	}
    }
    
    private Map<Integer,Hero> getHeroes() {
    	Gson gson = new Gson();
    	File heroDb = new File(filename);
    	Map<Integer,Hero> map = new HashMap<Integer,Hero>(); 
    		FileInputStream fis;
			try {
				fis = new FileInputStream(heroDb);
	    		InputStreamReader isr = new InputStreamReader(fis);
	    		BufferedReader bufferedReader = new BufferedReader(isr);
	    		StringBuilder sb = new StringBuilder();
	    		String line;
	    		while ((line = bufferedReader.readLine()) != null) {
	    			sb.append(line);
	    		}
	    		Type type = new TypeToken<Map<String, MarvelHero>>(){}.getType();
	    		map = gson.fromJson(sb.toString(), type);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return map;
    }
}
