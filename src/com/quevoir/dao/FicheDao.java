package com.quevoir.dao;

import java.util.List;

import com.quevoir.beans.Fiche;

public interface FicheDao {

    List<Fiche> getFiche( String id ) throws DAOException;

}