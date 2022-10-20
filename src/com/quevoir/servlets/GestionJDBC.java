package com.quevoir.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quevoir.bdd.ConnectJDBC;

/**
 * Servlet implementation class GestionJDBC
 */
@WebServlet( "/GestionJDBC" )
public class GestionJDBC extends HttpServlet {

    public static final String ATT_MESSAGES     = "messages";
    public static final String VUE              = "/WEB-INF/gestion_jdbc.jsp";

    private static final long  serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionJDBC() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        /* Initialisation de l'objet Java et récupération des messages */
        ConnectJDBC test = new ConnectJDBC();
        List<String> messages = test.executerTests( request );

        /* Enregistrement de la liste des messages dans l'objet requête */
        request.setAttribute( ATT_MESSAGES, messages );

        /* Transmission vers la page en charge de l'affichage des résultats */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet( request, response );
    }

}
