package com.quevoir.dao;

import java.util.List;

import com.quevoir.beans.GallerieItem;

public interface GallerieItemDao {

    List<GallerieItem> getAll( String type ) throws DAOException;

}