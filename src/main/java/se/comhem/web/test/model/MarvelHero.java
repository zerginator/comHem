package se.comhem.web.test.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class MarvelHero implements Hero, Serializable {

	private static final long serialVersionUID = 1L;
	@NotBlank(message = "name must not be blank!")
	private String name;
	@NotBlank(message = "weakness must not be blank!")
    private String weakness;
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
