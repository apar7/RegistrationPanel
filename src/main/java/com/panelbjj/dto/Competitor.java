package com.panelbjj.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Entity
public class Competitor implements Serializable {
    public Competitor() {
    }

    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    @Size(min = 2, max = 50)
    private String belt;
    @NotNull
    @Size(min = 2, max = 50)
    private String category;
    @NotNull
    @Size(min = 2, max = 50)
    private String weight;
    @NotNull
    @Size(min = 2, max = 50)
    private String club;
    @NotNull
    private Long registrationNumber;
    @NotNull
    private boolean enabled;
    @NotNull
    private boolean paid;
    @Id
    @GeneratedValue
    private int id;


    private String capitalizeName(String input) {
        return Pattern.compile("\\s+")
                .splitAsStream(input)
                .map(namePart -> Character.toUpperCase(namePart.charAt(0)) + namePart.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Long registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = capitalizeName(name);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

}

