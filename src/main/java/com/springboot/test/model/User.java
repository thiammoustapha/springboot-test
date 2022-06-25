package com.springboot.test.model;

import com.springboot.test.constraint.BirthDate;
import com.springboot.test.constraint.CountryOfResidence;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NotNull(message = "Please provide your user name")
    private String username;
    @NotNull(message = "Please provide your birth date")
    @BirthDate(message = "The birth date must be greater than or equal 18")
    @Past(message = "The date of birth must be in the past.")
    private Date birthDate;
    @NotNull(message = "Please provide your country of residence")
    @CountryOfResidence(message = "The country of residence must be France")
    private String countryOfResidence;
    private long phoneNumber;
    private String gender;

    public User() {
    }

    public User(String username, Date birthDate, String countryOfResidence, int phoneNumber, String gender) {
        this.username = username;
        this.birthDate = birthDate;
        this.countryOfResidence = countryOfResidence;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}