package com.capsuladigital.androidcore.data.model.person;

/**
 * Created by lvert on 27/02/2018.
 */

public class Person {
    private String personName;
    private String personLastName;
    private String personBirthDate;
    private String personGender;

    public Person(String personName, String personLastName, String personBirthDate, String personGender) {
        this.personName = personName;
        this.personLastName = personLastName;
        this.personBirthDate = personBirthDate;
        this.personGender = personGender;
    }

    public Person() {
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonBirthDay() {
        return personBirthDate;
    }

    public void setPersonBirthDay(String personBirthDay) {
        this.personBirthDate = personBirthDay;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }


}
