/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * A build bean object to map from and to db
 * has private properities such as
 * identifier, status, buildlog, timecreated
 * @author Simon
 */

 
public class Build {

    private String identifier;
    private String status;
    private String buildlog;
    private Instant timecreated;
    
    public Build(){}
    
    public void setIdentifier(String s){
        this.identifier = s;
    }

    /**
     * @param s current status of the build
     */
    public void setStatus(String s){
        this.status = s;
    }

    /**
     * @param s set the buildLog after it has been completed
     */
    public void setBuildlog(String s){
        this.buildlog = s;
    }
    
    public void setTimecreated(Instant s){
        this.timecreated = s;
    }

    /**
     * @return the build identifier
     */
    public String getIdentifier(){
        return identifier;
    }

    /**
     * @return the current status of the build
     */
    public String getStatus(){
        return status;
    }

    /**
     * @return the build log of the build
     */
    public String getBuildlog(){
        return buildlog;
    }
    
    public Instant getTimecreated(){
        return timecreated;
    }

}
