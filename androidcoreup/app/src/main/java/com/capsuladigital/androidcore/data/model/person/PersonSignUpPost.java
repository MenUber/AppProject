package com.capsuladigital.androidcore.data.model.person;

import java.util.List;

/**
 * Created by lvert on 7/03/2018.
 */

public class PersonSignUpPost extends PersonSignUpForm {

    private List<Integer> idTeam;

    public List<Integer> getTeams() {
        return idTeam;
    }

    public void setTeams(List<Integer> idTeam) {
        this.idTeam = idTeam;
    }

    public PersonSignUpPost(String personName, String personLastName, String personBirthDate, String personGender, String personEmail, String personPassword, List<Integer> idTeam) {
        super(personName, personLastName, personBirthDate, personGender, personEmail, personPassword);
        this.idTeam = idTeam;
    }
}
