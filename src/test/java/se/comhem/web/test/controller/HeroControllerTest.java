package se.comhem.web.test.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import se.comhem.web.test.controllers.HeroController;
import se.comhem.web.test.model.Gender;
import se.comhem.web.test.model.MarvelHero;
import se.comhem.web.test.services.HeroService;
import se.comhem.web.test.validation.ValidationError;

import com.google.gson.Gson;

public class HeroControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    HeroController controller;
    
    @Mock
    HeroService heroService;
    

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controller).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void testThatHeroesIsResponding() {
        try {
            this.mockMvc.perform(get("/heroes").accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        } catch(Exception e) {
            fail("Exception: " + e.fillInStackTrace());
        }
    }
    
    @Test
    public void testToSaveNewHero() {
    	 try {
    		MarvelHero hero = new MarvelHero("Hulk","Women",Gender.MAN);
    		String json = new Gson().toJson(hero);
            this.mockMvc.perform(post("/heroes/save")
            		.contentType(MediaType.APPLICATION_JSON_VALUE)
            		.content(json)
            		.accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().string("\"Congrats! New Hero created\""));
       } catch(Exception e) {
           fail("Exception: " + e.fillInStackTrace());
       }
    }
    
    @Test
    public void testThatNewHeroWontBeSavedWhenWeaknessIsMissing() {
    	Gson gson = new Gson();
		MarvelHero hero = new MarvelHero("Hulk",null,Gender.MAN);
    	ValidationError expectedError = new ValidationError("Validation failed. 1 error(s)");
    	expectedError.addValidationError("weakness must not be blank!");
    	String json = gson.toJson(hero);
		String jsonError = gson.toJson(expectedError);
    	try {
    		
            this.mockMvc.perform(post("/heroes/save")
            		.contentType(MediaType.APPLICATION_JSON_VALUE)
            		.content(json)
            		.accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is4xxClientError())
                    .andExpect(content().json(jsonError));
       } catch(Exception e) {
           fail("Exception: " + e.fillInStackTrace());
       }
    }
    
    @Test
    public void testThatGetSingleHeroByNumber() {
    	MarvelHero hero = new MarvelHero("Hulk","Women",Gender.MAN);
    	Mockito.when(heroService.getHero(Mockito.anyInt())).thenReturn(hero);
    	try {
            this.mockMvc.perform(get("/heroes/1")
            		.accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andExpect(content().json(new Gson().toJson(hero)));
       } catch(Exception e) {
           fail("Exception: " + e.fillInStackTrace());
       }
    }
    
    @Test
    public void testThatGetSingleHeroThrowsAnErrorWhenNoIntegerWasSend() {
    	try {
            this.mockMvc.perform(get("/heroes/WTH")
            		.accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(content().string("\"Please insert a number for a hero , for example YOURDOMAIN/heroes/1\""));
       } catch(Exception e) {
           fail("Exception: " + e.fillInStackTrace());
       }
    }
}
