package com.panelbjj.dto;

import com.panelbjj.dto.Competitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Registration implements Serializable {


    private List<Competitor> competitorList = new ArrayList<>();
    private Competitor competitor;
    private String email;
    private String clubTyped;
    private String club;
    private String clubChosen;
    private String city;
    private int number;


    public Registration() {
    }

    public void validate() throws IllegalArgumentException {
        if (!clubChosen.equals("")) club = clubChosen + " " + city;
        else if (!clubTyped.equals("")) club = clubTyped + " " + city;
        else throw new IllegalArgumentException();
        if (number <= 0 || number > 100) throw new IllegalArgumentException();
        for (int i = 1; i <= number; i++) {
            Competitor competitor = new Competitor();
            competitorList.add(competitor);
        }
    }
   

    public void setEmail(String email) {
        this.email = email;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getClubChosen() {
        return clubChosen;
    }

    public String getClubTyped() {
        return clubTyped;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setClubChosen(String clubChosen) {
        this.clubChosen = clubChosen;
    }

    public void setClubTyped(String clubTyped) {
        this.clubTyped = clubTyped;
    }

    public String getCity() {
        return city;
    }

    public String getClub() {
        return club;
    }

    public int getNumber() {
        return number;
    }

    public List<Competitor> getCompetitorList() {
        return competitorList;
    }

    public String getEmail() {
        return email;
    }

    public void setCompetitorList(List<Competitor> competitorList) {
        this.competitorList = competitorList;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

}
