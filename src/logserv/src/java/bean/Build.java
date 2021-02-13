/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.Time;

/**
 * Class to hold an instance of a
 * repository build
 * @author Simon
 */
public class Build {
    
    private String identifier;
    private String status;
    private String buildlog;
    private Time timecreated;

    /**
     * @param s string that will identify the build
     */
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

    /**
     * @param s set the time when the build was created
     */
    public void setTimecreated(Time s){
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

    /**
     * @return the time of creation
     */
    public Time getTimecreated(){
        return timecreated;
    }

}
