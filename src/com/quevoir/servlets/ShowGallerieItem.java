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

import com.quevoir.beans.GallerieItem;
import com.quevoir.dao.DAOFactory;
import com.quevoir.dao.GallerieItemDao;

public class ShowGallerieItem extends HttpServlet {
    public static final String ATT_MESSAGES     = "messages";
    public static final String CONF_DAO_FACTORY = "daofactory";
    public static final String ATT_FORM         = "form";
    public static final String VUE              = "/WEB-INF/gallerie.jsp";

    private GallerieItemDao    itemDao;

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.itemDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getGallerieItemDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String type = "pays";
        type = request.getParameter( "type" ) == null ? "pays" : request.getParameter( "type" );
        List<GallerieItem> listeItems = itemDao.getAll( type );
        Map<Long, GallerieItem> mapItems = new HashMap<Long, GallerieItem>();
        for ( GallerieItem item : listeItems ) {
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