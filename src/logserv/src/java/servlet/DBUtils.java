package servlet;

import bean.Build;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.Pragma;

/**
 * Data Base utility functions
 * @author Lucas
 */
public class DBUtils {

    final String sqlFetchWithId = "SELECT id, identifier, status, buildlog, timecreated FROM build WHERE id = ?;";
    final String sqlFetchNoId = "SELECT id, identifier, status, buildlog, timecreated FROM build";

    /**
     * Query the data base with ID
     * @param id
     * @return an ArrayList of builds
     */
    public ArrayList<Build> fetchWithId(int id){

        ArrayList<Build> builds = new ArrayList<>();

        Connection conn = this.connect("./../../../../../logs.db");

        try {
            PreparedStatement pstmt = conn.prepareStatement(sqlFetchWithId);
            pstmt.setInt(1, id);
            ResultSet res = pstmt.executeQuery();

            while(res.next()){
                Build tempBuild = new Build();
                tempBuild.setIdentifier(res.getString("identifier"));
                tempBuild.setStatus(res.getString("status"));
                tempBuild.setBuildlog(res.getString("buildlog"));
                Timestamp ts = res.getTimestamp("timecreated");
                tempBuild.setTimecreated(ts.toInstant());
                builds.add(tempBuild);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(conn != null)
                    conn.close();
            }
            catch(SQLException e)
            {
                System.err.println(e.getMessage());
            }
        }
        return builds;
    }

    /**
     * Query the data base without ID
     * @return array of builds
     */
    public ArrayList<Build> fetchWithNoId(){
        ArrayList<Build> builds = new ArrayList<>();
        Connection conn = this.connect("./../../../../../logs.db");

        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sqlFetchNoId);

            while(res.next()){
                Build tempBuild = new Build();
                tempBuild.setIdentifier(res.getString("identifier"));
                tempBuild.setStatus(res.getString("status"));
                tempBuild.setBuildlog(res.getString("buildlog"));
                Timestamp ts = res.getTimestamp("timecreated");
                tempBuild.setTimecreated(ts.toInstant());
                builds.add(tempBuild);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(conn != null)
                    conn.close();
            }
            catch(SQLException e)
            {
                System.err.println(e.getMessage());
            }
        }
        return builds;
    }

    public void insertLog(String identifier, String status, String log){
        String insertionString = "INSERT INTO build (\n"
                + "identifier, status, buildlog)\n"
                + "VALUES (?, ?, ?)";
        Connection conn = this.connect("logs.db");

        try {
            PreparedStatement pstmt = conn.prepareStatement(insertionString);
            pstmt.setString(1, identifier);
            pstmt.setString(2, status);
            pstmt.setString(3, log);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try
            {
                if(conn != null)
                    conn.close();
            }
            catch(SQLException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }

    private Connection connect(String location){
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        Properties properties = sqLiteConfig.toProperties();
        properties.setProperty(Pragma.DATE_STRING_FORMAT.pragmaName, "yyyy-MM-dd HH:mm:ss");
        String url = "jdbc:sqlite:" + location;
        String tableCreationString = "CREATE TABLE IF NOT EXISTS build (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "identifier TEXT,\n"
                + "    status TEXT,\n"
                + "    buildlog TEXT,\n"
                + "    timecreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n"
                + ");\n";

        Connection retConn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url, properties);
            if (conn != null){
                DatabaseMetaData meta = conn.getMetaData();
                Statement stmt = conn.createStatement();
                stmt.execute(tableCreationString);
                retConn = conn;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return retConn;
    }
}
