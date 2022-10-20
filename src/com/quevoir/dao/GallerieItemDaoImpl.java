package com.quevoir.dao;

import static com.quevoir.dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.quevoir.beans.GallerieItem;

public class GallerieItemDaoImpl implements GallerieItemDao {

    private static final String SQL_SELECT_PAYS      = "SELECT id, name, libelle FROM pays ORDER BY name asc";
    private static final String SQL_SELECT_CONTINENT = "SELECT id, name, libelle FROM continent ORDER BY name asc";
    private static final String SQL_SELECT_THEME     = "SELECT id, name, libelle FROM theme ORDER BY name asc";
    private static final String SQL_SELECT_TOP_PAYS  = "SELECT id, name, libelle FROM tops WHERE type=1 ORDER BY name asc";
    private static final String SQL_SELECT_TOP_THEME = "SELECT id, name, libelle FROM tops WHERE type=0 ORDER BY name asc";

    private DAOFactory          daoFactory;

    GallerieItemDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /*
     * MÃ©thode gÃ©nÃ©rique pour retourner les items depuis la base de donnÃ©es,
     * correspondant Ã la requÃªte SQL donnÃ©e prenant en paramÃ¨tres les objets
     * passÃ©s en argument.
     */
    @Override
    public List<GallerieItem> getAll( String type ) throws DAOException {
        Connection connexion = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        String sql = "";
        List<GallerieItem> item = new ArrayList<GallerieItem>();

        try {
            /* RÃ©cupÃ©ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            try {
                stmt = connexion.createStatement();
            } catch ( SQLException e1 ) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            /*
             * PrÃ©paration de la requÃªte avec les objets passÃ©s en arguments
             * et exÃ©cution.
             */
            // preparedStatement = connexion.prepareStatement( SQL_SELECT_PAYS
            // );
            switch ( type ) {
            case "pays": {
                sql = SQL_SELECT_PAYS;
                break;
            }
            case "continent": {
                sql = SQL_SELECT_CONTINENT;
                break;
            }
            case "theme": {
                sql = SQL_SELECT_THEME;
                break;
            }
            case "toppays": {
                sql = SQL_SELECT_TOP_PAYS;
                break;
            }
            case "toptheme": {
                sql = SQL_SELECT_TOP_THEME;
                break;
            }

            }
            resultSet = stmt.executeQuery( sql );
            // resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de donnÃ©es retournÃ©e dans le ResultSet */
            while ( resultSet.next() ) {
                item.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, stmt, connexion );
        }

        return item;
    }

    /*
     * Simple mÃ©thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des pays (un ResultSet) et un
     * bean pays.
     */
    private static GallerieItem map( ResultSet resultSet ) throws SQLException {
        GallerieItem item = new GallerieItem();
        item.setId( resultSet.getLong( "id" ) );
        item.setName( resultSet.getString( "name" ) );
        item.setLibelle( resultSet.getString( "libelle" ) );
        return item;
    }

}