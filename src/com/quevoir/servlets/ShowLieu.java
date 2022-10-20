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

import com.quevoir.beans.Lieu;
import com.quevoir.dao.DAOFactory;
import com.quevoir.dao.LieuDao;

public class ShowLieu extends HttpServlet {
    public static final String ATT_MESSAGES     = "messages";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/lieu.jsp";

    private LieuDao            lieuDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.lieuDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getLieuDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String type, id;
        type = request.getParameter( "type" ) == null ? "pays" : request.getParameter( "type" );
        id = request.getParameter( "id" ) == null ? "1" : request.getParameter( "id" );
        List<Lieu> listeItems = lieuDao.getAll( type, id );
        Map<Long, Lieu> mapItems = new HashMap<Long, Lieu>();
        for ( Lieu item : listeItems ) {
            mapItems.put( item.getId(), item );
        }
        session.setAttribute( "items", mapItems );
        session.setAttribute( "type", type );
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}