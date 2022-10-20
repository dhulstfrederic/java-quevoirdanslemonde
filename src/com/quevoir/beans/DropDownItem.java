package com.quevoir.beans;

import java.io.Serializable;

public class DropDownItem implements Serializable {

    Long id;

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

    public String getLink() {
        return link;
    }

    public void setLink( String link ) {
        this.link = link;
    }

    String libelle;
    String link;

}
