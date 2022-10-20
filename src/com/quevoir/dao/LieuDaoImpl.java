package com.quevoir.dao;

import static com.quevoir.dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.quevoir.beans.Lieu;
import com.quevoir.beans.Top;

public class LieuDaoImpl implements LieuDao {

    private static final String SQL_SELECT_LIEUX_PAYS           = "SELECT lieux.id as lieux_id, lieux.name as lieux_name, lieux.libelle as lieux_libelle, continent.libelle as continent_libelle, pays.libelle as pays_libelle, theme.libelle as theme_libelle  FROM lieux, continent, pays, theme  WHERE continent.id = lieux.id_continent and pays.id = lieux.id_pays and theme.id = lieux.id_theme  and lieux.id_pays = ?  and lieux.id_theme <> 30 ORDER BY lieux.libelle";
    private static final String SQL_SELECT_LIEUX_CONTINENT      = "SELECT lieux.id as lieux_id, lieux.name as lieux_name, lieux.libelle as lieux_libelle, continent.libelle as continent_libelle, pays.libelle as pays_libelle, theme.libelle as theme_libelle  FROM lieux, continent, pays, theme  WHERE continent.id = lieux.id_continent and pays.id = lieux.id_pays and theme.id = lieux.id_theme  and lieux.id_continent = ?  and lieux.id_theme <> 30 ORDER BY lieux.libelle";
    private static final String SQL_SELECT_LIEUX_THEME          = "SELECT lieux.id as lieux_id, lieux.name as lieux_name, lieux.libelle as lieux_libelle, continent.libelle as continent_libelle, pays.libelle as pays_libelle, theme.libelle as theme_libelle  FROM lieux, continent, pays, theme  WHERE continent.id = lieux.id_continent and pays.id = lieux.id_pays and theme.id = lieux.id_theme  and lieux.id_theme = ?  and lieux.id_theme <> 30 ORDER BY lieux.libelle";
    private static final String SQL_SELECT_TOP                  = "SELECT tops.top as tops_id FROM tops WHERE tops.id = ?";
    private static final String SQL_SELECT_LIEUX_TOP_PAYS_THEME = "SELECT lieux.id as lieux_id, lieux.name as lieux_name, lieux.libelle as lieux_libelle, continent.libelle as continent_libelle, pays.libelle as pays_libelle, theme.libelle as theme_libelle  FROM lieux,continent,pays, theme  WHERE continent.id = lieux.id_continent and pays.id = lieux.id_pays and theme.id = lieux.id_theme";

    private DAOFactory          daoFactory;

    LieuDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /*
     * MÃ©thode gÃ©nÃ©rique pour retourner les items depuis la base de donnÃ©es,
     * correspondant Ã la requÃªte SQL donnÃ©e prenant en paramÃ¨tres les objets
     * passÃ©s en argument.
     */
    @Override
    public List<Lieu> getAll( String type, String id ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "";
        List<Lieu> item = new ArrayList<Lieu>();

        try {
            /* RÃ©cupÃ©ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            switch ( type ) {
            case "pays": {
                sql = SQL_SELECT_LIEUX_PAYS;
                break;
            }
            case "continent": {
                sql = SQL_SELECT_LIEUX_CONTINENT;
                break;
            }
            case "theme": {
                sql = SQL_SELECT_LIEUX_THEME;
                break;
            }
            case "toptheme":
            case "toppays": {
                sql = SQL_SELECT_TOP;
                preparedStatement = connexion.prepareStatement( sql );
                preparedStatement.setString( 1, id );
                resultSet = preparedStatement.executeQuery();
                while ( resultSet.next() ) {
                    id = mapTop( resultSet ).getTop();
                }

                sql = SQL_SELECT_LIEUX_TOP_PAYS_THEME;
                break;
            }
            }
            if ( type.equals( "toppays" ) || type.equals( "toptheme" ) ) {
                Statement stmt = connexion.createStatement();
                resultSet = stmt
                        .executeQuery( sql + " and lieux.id IN (" + id + ") order by Field(lieux.id," + id + ")" );

            } else {
                preparedStatement = connexion.prepareStatement( sql );
                preparedStatement.setString( 1, id );
                resultSet = preparedStatement.executeQuery();
            }
            /* Parcours de la ligne de donnÃ©es retournÃ©e dans le ResultSet */
            while ( resultSet.next() ) {
                item.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return item;
    }

    /*
     * Simple mÃ©thode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des pays (un ResultSet) et un
     * bean pays.
     */
    private static Lieu map( ResultSet resultSet ) throws SQLException {
        Lieu item = new Lieu();
        item.setId( resultSet.getLong( "lieux_id" ) );
        item.setName( resultSet.getString( "lieux_name" ) );
        item.setLibelle( resultSet.getString( "lieux_libelle" ) );
        item.setPaysLibelle( resultSet.getString( "pays_libelle" ) );
        item.setContinentLibelle( resultSet.getString( "continent_libelle" ) );
        item.setThemeLibelle( resultSet.getString( "theme_libelle" ) );
        return item;
    }

    private static Top mapTop( ResultSet resultSet ) throws SQLException {
        Top item = new Top();
        item.setTop( resultSet.getString( "tops_id" ) );
        return item;
    }

}