/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.time.LocalDate;

/**
 *
 * @author Simon
 */
public class Build {
    
    private String identifier;
    private String status;
    private String buildlog;
    private LocalDate timecreated;
    
    public Build(){}
    
    public void setIdentifier(String s){
        this.identifier = s;
    }
    public void setStatus(String s){
        this.status = s;
    }
    public void setBuildlog(String s){
        this.buildlog = s;
    }
    
    public void setTimecreated(LocalDate s){
        this.timecreated = s;
    }
    
    public String getIdentifier(){
        return identifier;
    }
    
    public String getStatus(){
        return status;
    }
    
    public String getBuildlog(){
        return buildlog;
    }
    
    public LocalDate getTimecreated(){
        return timecreated;
    }

}
