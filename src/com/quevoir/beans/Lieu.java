package com.quevoir.beans;

import java.io.Serializable;

public class Lieu implements Serializable {

    Long   id;
    String name;
    String libelle;
    String paysLibelle;
    String continentLibelle;
    String themeLibelle;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle( String libelle ) {
        this.libelle = libelle;
    }

    public String getPaysLibelle() {
        return paysLibelle;
    }

    public void setPaysLibelle( String paysLibelle ) {
        this.paysLibelle = paysLibelle;
    }

    public String getContinentLibelle() {
        return continentLibelle;
    }

    public void setContinentLibelle( String continentLibelle ) {
        this.continentLibelle = continentLibelle;
    }

    public String getThemeLibelle() {
        return themeLibelle;
    }

    public void setThemeLibelle( String themeLibelle ) {
        this.themeLibelle = themeLibelle;
    }

}
