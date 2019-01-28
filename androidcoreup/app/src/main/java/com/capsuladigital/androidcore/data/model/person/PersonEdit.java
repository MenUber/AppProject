package com.capsuladigital.androidcore.data.model.person;

/**
 * Created by lvert on 27/02/2018.
 */

public class PersonEdit extends Person {

    private String personImageUrl;

    public PersonEdit(String personName, String personLastName, String personBirthDate, String personGender, String personImageUrl) {
        super(personName, personLastName, personBirthDate, personGender);
        this.personImageUrl = personImageUrl;
    }

    public PersonEdit() {
    }

    public String getPersonImageUrl() {
        return personImageUrl;
    }

    public void setPersonImageUrl(String personImageUrl) {
        this.personImageUrl = personImageUrl;
    }
}
