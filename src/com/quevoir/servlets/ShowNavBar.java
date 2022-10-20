package com.quevoir.servlets;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.quevoir.beans.DropDownItem;
import com.quevoir.beans.NavBarItem;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * Servlet implementation class ShowNavBar
 */
@WebServlet( "/ShowNavBar" )
public class ShowNavBar extends HttpServlet {

    public static final String             VUE              = "/WEB-INF/navbar.jsp";

    private static final long              serialVersionUID = 1L;
    private static Map<Long, NavBarItem>   mapNavBarItems;
    private static ArrayList<DropDownItem> dropwDownList;
    private static NavBarItem              item;
    private static long                    cpt;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowNavBar() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */

    private static void parseMenuItemObject( JSONObject menuItem ) {
        JSONObject menuItemObject = (JSONObject) menuItem.get( "menuItem" );

        item = new NavBarItem();
        item.setId( cpt );
        cpt++;

        String libelle = (String) menuItemObject.get( "libelle" );
        System.out.println( libelle );
        item.setLibelle( libelle );

        JSONArray dropDownListJson = (JSONArray) menuItemObject.get( "dropdown" );

        // Iterate over dropdown array

        dropwDownList = new ArrayList<DropDownItem>();
        dropDownListJson.forEach( dropDown -> parseDropDownItemObject( (JSONObject) dropDown ) );
        DropDownItem[] dd = dropwDownList.toArray( new DropDownItem[dropwDownList.size()] );
        item.setDropwDownList( dd );

        mapNavBarItems.put( item.getId(), item );

    }

    private static void parseDropDownItemObject( JSONObject dropDownItem ) {
        // JSONObject dropDownItemObject = (JSONObject) dropDownItem.get(
        // "dropdown" );

        DropDownItem ddItem = new DropDownItem();
        String libelle = (String) dropDownItem.get( "libelle" );
        ddItem.setLibelle( libelle );
        System.out.println( libelle );
        String link = (String) dropDownItem.get( "link" );
        ddItem.setLink( link );
        System.out.println( link );

        dropwDownList.add( ddItem );

    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        HttpSession session = request.getSession();
        JSONParser jsonParser = new JSONParser();
        mapNavBarItems = new HashMap<Long, NavBarItem>();

        try ( FileReader reader = new FileReader(
                "C:\\eclipse-workspace\\quevoirdanslemonde\\WebContent\\WEB-INF\\navbar.json" ) ) {
            // Read JSON file
            Object obj = jsonParser.parse( reader );

            JSONArray menuItemsList = (JSONArray) obj;
            // System.out.println( menuItemsList );

            // Iterate over menuItem array
            cpt = 0;
            menuItemsList.forEach( menuItem -> parseMenuItemObject( (JSONObject) menuItem ) );

            session.setAttribute( "mapNavBarItems", mapNavBarItems );

        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        } catch ( ParseException e ) {
            e.printStackTrace();
        } catch ( org.json.simple.parser.ParseException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestDispatcher view = request.getRequestDispatcher( VUE );
        view.include( request, response );
        // this.getServletContext().getRequestDispatcher( VUE ).forward(
        // request, response );
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
