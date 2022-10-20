package com.quevoir.dao;

import static com.quevoir.dao.DAOUtilitaire.fermeturesSilencieuses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quevoir.beans.Fiche;

public class FicheDaoImpl implements FicheDao {

    private static final String SQL_SELECT_LIEUX_FICHE = "SELECT lieux.id as lieux_id, lieux.name as lieux_name, lieux.libelle as lieux_libelle, lieux.descriptif as lieux_descriptif, lieux.libelle2 as lieux_libelle2, lieux.facts as lieux_facts, lieux.why as lieux_why, lieux.map as lieux_map, lieux.virtual as lieux_virtual, lieux.recommanded_site as lieux_recommanded_site, continent.libelle as continent_libelle, pays.libelle as pays_libelle,pays.recommanded_site as pays_recommanded_site, pays.cout as pays_cout,pays.sante as pays_sante,pays.securite as pays_securite, DATE_FORMAT(lieux.date_published_date,'%d/%m/%Y') as lieux_date_published_date, pays.guide as pays_guide FROM lieux, continent, pays WHERE continent.id = lieux.id_continent and pays.id = lieux.id_pays and lieux.id=  ? ORDER BY lieux.libelle";

    private DAOFactory          daoFactory;

    FicheDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /*
     * MÃ©thode gÃ©nÃ©rique pour retourner les items depuis la base de donnÃ©es,
     * correspondant Ã la requÃªte SQL donnÃ©e prenant en paramÃ¨tres les objets
     * passÃ©s en argument.
     */
    @Override
    public List<Fiche> getFiche( String id ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "";
        List<Fiche> item = new ArrayList<Fiche>();

        try {
            /* RÃ©cupÃ©ration d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            sql = SQL_SELECT_LIEUX_FICHE;
            preparedStatement = connexion.prepareStatement( sql );
            preparedStatement.setString( 1, id );
            resultSet = preparedStatement.executeQuery();
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
    private static Fiche map( ResultSet resultSet ) throws SQLException {
        Fiche item = new Fiche();
        item.setId( resultSet.getLong( "lieux_id" ) );
        item.setName( resultSet.getString( "lieux_name" ) );
        item.setLibelle( resultSet.getString( "lieux_libelle" ) );
        item.setDescriptif( resultSet.getString( "lieux_descriptif" ) );
        item.setFacts( resultSet.getString( "lieux_facts" ) );
        item.setWhy( resultSet.getString( "lieux_why" ) );
        item.setMap( resultSet.getString( "lieux_map" ) );
        item.setVirtual( resultSet.getString( "lieux_virtual" ) );
        item.setRecommanded_site( resultSet.getString( "lieux_recommanded_site" ) );
        item.setContinent_libelle( resultSet.getString( "continent_libelle" ) );
        item.setPays_libelle( resultSet.getString( "pays_libelle" ) );
        item.setPays_cout( resultSet.getString( "pays_cout" ) );
        item.setPays_sante( resultSet.getString( "pays_sante" ) );
        item.setPays_securite( resultSet.getString( "pays_securite" ) );
        item.setLieux_date_published_date( resultSet.getString( "lieux_date_published_date" ) );
        item.setPays_guide( resultSet.getString( "pays_guide" ) );
        return item;
    }

}