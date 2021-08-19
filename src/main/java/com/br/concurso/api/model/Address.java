package com.br.concurso.api.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String publicplace;
    private String number;
    private String complement;
    private String neighborhood;
    private String zipcode;
    private String city;
    private String state;

    public String getPublicplace() {
        return publicplace;
    }

    public void setPublicplace(String publicplace) {
        this.publicplace = publicplace;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
