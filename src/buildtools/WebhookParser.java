package buildtools;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;


public class WebhookParser {
    private Gson gson = new Gson();
    private LinkedTreeMap<String, Object> webhook;
    private String repoBranch;
    private String repoURL;
    private String repoName;
    private String repoOwner;
    private String commitID;


    WebhookParser(BufferedReader webhookReader){
        try {
            webhook = gson.fromJson(webhookReader, LinkedTreeMap.class);
            repoBranch = branch(webhook);
            repoURL = (String) getInnerJson(webhook,"repository").get("html_url");
            repoName = (String) getInnerJson(webhook,"repository").get("name");
            repoOwner = (String)getInnerJson(webhook,"owner").get("name");
            commitID = (String) webhook.get("after");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /*
        @Returns branch name given in webhook
     */
    private String branch(LinkedTreeMap json){
        String refVal = (String) json.get("ref");
        String[] splitRef = refVal.split("/");
        return splitRef[splitRef.length-1];
    }


    /*
        @Returns inner Json in nested Json structure given key
     */
    private LinkedTreeMap getInnerJson(LinkedTreeMap json, String key){
        return (LinkedTreeMap) json.get(key);
    }


    /* Get repo owner */
    public String getRepoOwner(){ return repoOwner; }

    /* Get repo URL */
    public String getrepoURL(){ return repoURL; }

    /* Get repo name */
    public String getrepoName(){ return repoName; }

    /* Get Commit ID */
    public String getCommitID(){ return commitID; }

    /* Get branch */
    public String getBranch(){ return repoBranch; }

}

