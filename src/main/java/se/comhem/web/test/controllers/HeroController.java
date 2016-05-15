package se.comhem.web.test.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import se.comhem.web.test.model.Hero;
import se.comhem.web.test.model.MarvelHero;
import se.comhem.web.test.services.HeroService;
import se.comhem.web.test.validation.ValidationError;
import se.comhem.web.test.validation.ValidationErrorBuilder;

@RestController
@RequestMapping("heroes")
public class HeroController {

    @Autowired
    private HeroService heroService;

    @RequestMapping(method = RequestMethod.GET , produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer,Hero>> listHeroes() {
        return new ResponseEntity<Map<Integer,Hero>>(heroService.getList(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHero(@PathVariable String id) {
        try {
            return new ResponseEntity<Hero>(heroService.getHero(Integer.parseInt(id)), HttpStatus.OK);
        } catch (NumberFormatException nfe) {
            return ResponseEntity.badRequest().body("Please insert a number for a hero , for example YOURDOMAIN/heroes/1");
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHero(@Valid @RequestBody MarvelHero hero) {
        heroService.save(hero);
        return ResponseEntity.ok("Congrats! New Hero created");
    }
    
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ValidationError handleException(MethodArgumentNotValidException exception) {
        return createValidationError(exception);
    }

    private ValidationError createValidationError(MethodArgumentNotValidException e) {
        return ValidationErrorBuilder.fromBindingErrors(e.getBindingResult());
    }
}
