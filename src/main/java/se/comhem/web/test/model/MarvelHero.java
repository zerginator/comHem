package se.comhem.web.test.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class MarvelHero implements Hero, Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull(message = "name must not be blank!")
	private String name;
	@NotNull(message = "weakness must not be blank!")
    private String weakness;
	@NotNull(message = "gender must not be blank!")
    private Gender gender;

    public MarvelHero() { }

    public MarvelHero(String name, String weakness, Gender gender) {
        this.name = name;
        this.weakness = weakness;
        this.gender = gender;
    }

    public void setName(String name) {
		this.name = name;
	}

	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getName() {
        return name;
    }

    public String getWeakness() {
        return weakness;
    }

    public Gender getGender() {
        return gender;
    }
}
