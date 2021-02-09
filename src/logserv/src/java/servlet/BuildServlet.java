/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import bean.Build;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Simon
 */
public class BuildServlet extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //establish db connection 

        ArrayList<Build> builds = new ArrayList<Build>();
        String queryId = request.getParameter("id");
        if(queryId !=null) {
            //Select from build table by queryId
            //add object to builds arrayList
        } else {
            //Select all from build table
            //add all objects to builds arrayList
        }
        
        request.setAttribute("buildList", builds);
        getServletConfig().getServletContext().getRequestDispatcher("/builds.jsp").forward(request,response);
        
        }
    
}
