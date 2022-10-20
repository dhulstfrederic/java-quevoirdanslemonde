package com.quevoir.dao;

import java.util.List;

import com.quevoir.beans.Lieu;

public interface LieuDao {

    List<Lieu> getAll( String type, String id ) throws DAOException;

}