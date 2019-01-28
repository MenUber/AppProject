package com.capsuladigital.androidcore.data.model.person;

/**
 * Created by lvert on 27/02/2018.
 */

public class PersonProfile extends PersonEdit {

    private String personEmail;

    public PersonProfile(String personName, String personLastName, String personBirthDate, String personGender, String personImageUrl, String personEmail) {
        super(personName, personLastName, personBirthDate, personGender, personImageUrl);
        this.personEmail = personEmail;
    }

    public PersonProfile() {
    }

    public void setPersonEdit(PersonEdit personEdit) {
        setPersonName(personEdit.getPersonName());
        setPersonLastName(personEdit.getPersonLastName());
        setPersonBirthDay(personEdit.getPersonBirthDay());
        setPersonGender(personEdit.getPersonGender());
        setPersonImageUrl(personEdit.getPersonImageUrl());
    }



    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonImageUrl(String personEmail) {
        this.personEmail = personEmail;
    }
}
