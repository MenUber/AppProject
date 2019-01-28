package com.capsuladigital.androidcore.data.model.person;

/**
 * Created by lvert on 27/02/2018.
 */

public class PersonSignUpForm extends Person {
    private String personEmail;
    private String personPassword;

    public PersonSignUpForm(String personName, String personLastName, String personBirthDate, String personGender, String personEmail, String personPassword) {
        super(personName, personLastName, personBirthDate, personGender);
        this.personEmail = personEmail;
        this.personPassword = personPassword;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonPassword() {

        return personPassword;
    }

    public void setPersonPassword(String personPassword) {
        this.personPassword = personPassword;
    }


}
