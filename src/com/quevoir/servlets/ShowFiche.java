package com.quevoir.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.quevoir.beans.Fiche;
import com.quevoir.dao.DAOFactory;
import com.quevoir.dao.FicheDao;

public class ShowFiche extends HttpServlet {
    public static final String ATT_MESSAGES     = "messages";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/fiche.jsp";

    private FicheDao           ficheDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.ficheDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getFicheDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String type, id;
        id = request.getParameter( "id" ) == null ? "1" : request.getParameter( "id" );
        List<Fiche> listeItems = ficheDao.getFiche( id );
        Map<Long, Fiche> mapItems = new HashMap<Long, Fiche>();
        for ( Fiche item : listeItems ) {
            mapItems.put( item.getId(), item );
        }
        session.setAttribute( "items", mapItems );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}