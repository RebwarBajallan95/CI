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

/**
 *
 * @author Simon
 */
public class BuildServlet extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Build> builds = new ArrayList<Build>();
        String queryId = request.getParameter("id");

        String sqlFetchWithId = "SELECT id, identifier, status, buildlog, timecreated FROM builds WHERE id = ?;";
        String sqlFetchNoId = "SELECT id, identifier, status, buildlog, timecreated FROM builds";

        if(queryId !=null) {
            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sqlFetchWithId)){

                 pstmt.setInt(1, Integer.parseInt(queryId));
                 ResultSet res = pstmt.executeQuery();

                 while(res.next()){
                     Build tempBuild = new Build();
                     tempBuild.setIdentifier(res.getString("identifier"));
                     tempBuild.setStatus(res.getString("status"));
                     tempBuild.setBuildlog(res.getString("buildlog"));
                     tempBuild.setTimecreated(res.getTime("timecreated"));
                     builds.add(tempBuild);
                 }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            try (Connection conn = connect();
                 Statement stmt = conn.createStatement()){

                ResultSet res = stmt.executeQuery(sqlFetchNoId);

                while(res.next()){
                    Build tempBuild = new Build();
                    tempBuild.setIdentifier(res.getString("identifier"));
                    tempBuild.setStatus(res.getString("status"));
                    tempBuild.setBuildlog(res.getString("buildlog"));
                    tempBuild.setTimecreated(res.getTime("timecreated"));
                    builds.add(tempBuild);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        request.setAttribute("buildList", builds);
        getServletConfig().getServletContext().getRequestDispatcher("/builds.jsp").forward(request,response);
        
        }

    private Connection connect(){
        String url = "jdbc:sqlite:./db/logs.db";
        String tableCreationString = "CREATE TABLE IF NOT EXISTS build (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "identifier TEXT,\n"
                + "    status TEXT,\n"
                + "    buildlog TEXT,\n"
                + "    timecreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n"
                + ");\n";

        Connection retConn = null;

        try (Connection conn = DriverManager.getConnection(url)){
            if (conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                Statement stmt = conn.createStatement();
                stmt.execute(tableCreationString);
                retConn = conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retConn;
    }
}
