/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import bean.Build;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlet.DBUtils.*;

/**
 * Class to implement a basic API
 * to handle the requests and responses
 * to and from the CI server.
 * @author Simon, Lucas
 */
public class BuildServlet extends HttpServlet {
    
    /**
     * This is the get endpoint for buildservlet
     * the url request are filtered if they contains queryId
     * a single build is fetch from the db else fetch all
     * Finally builds arraylist is passed to the builds.jsp file
     * @return void redirecting to builds.jsp
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Build> builds = new ArrayList<Build>();
        String queryId = request.getParameter("id");
        servlet.DBUtils dbu = new servlet.DBUtils();
        
        if(queryId !=null) {
            builds = dbu.fetchWithId(Integer.parseInt(queryId));
        } else {
            builds = dbu.fetchWithNoId();
        }

        request.setAttribute("buildList", builds);
        getServletConfig().getServletContext().getRequestDispatcher("/builds.jsp").forward(request,response);
    }
}
