package com.quevoir.beans;

import java.io.Serializable;

public class Top implements Serializable {

    Long   id;
    String name;
    String libelle;
    String top;
    String selection;
    String type;

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

    public String getTop() {
        return top;
    }

    public void setTop( String top ) {
        this.top = top;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection( String selection ) {
        this.selection = selection;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

}
