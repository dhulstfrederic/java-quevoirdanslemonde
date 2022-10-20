package com.quevoir.beans;

import java.io.Serializable;

public class NavBarItem implements Serializable {

    Long         id;
    String       libelle;
    DropDownItem dropwDownList[];

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle( String libelle ) {
        this.libelle = libelle;
    }

    public DropDownItem[] getDropwDownList() {
        return dropwDownList;
    }

    public void setDropwDownList( DropDownItem[] dropwDownList ) {
        this.dropwDownList = dropwDownList;
    }

}
