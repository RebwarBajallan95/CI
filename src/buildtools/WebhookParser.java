package buildtools;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;

/**
 * Class to parse branch information
 * from a Webhook.
 */
public class WebhookParser {
    private Gson gson = new Gson();
    private LinkedTreeMap<String, Object> webhook;
    private String repoBranch;
    private String repoURL;
    private String repoName;
    private String namePusher;
    private String commitID;
    private String emailPusher;


    /**
     * Construct a Webhook Parser that
     * will contain information about
     * a branch attempting to make a
     * push to the main branch of
     * the repository.
     * @param webhookReader
     */
    public WebhookParser(BufferedReader webhookReader){
        try {
            webhook = gson.fromJson(webhookReader, LinkedTreeMap.class);
            repoBranch = branch(webhook);
            repoURL = (String) getInnerJson(webhook,"repository").get("html_url");
            repoName = (String) getInnerJson(webhook,"repository").get("name");
            namePusher = (String)getInnerJson(webhook,"pusher").get("name");
            emailPusher = (String)getInnerJson(webhook,"pusher").get("email");
            commitID = (String) webhook.get("after");
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * @param json file containing information about the branch
     * @return name of the branch
     */
    private String branch(LinkedTreeMap json){
        String refVal = (String) json.get("ref");
        String[] splitRef = refVal.split("/");
        return splitRef[splitRef.length-1];
    }

    /**
     * @param json the nested JSON file
     * @param key to fetch the inner JSON file
     * @return inner JSON file
     */
    private LinkedTreeMap getInnerJson(LinkedTreeMap json, String key){
        return (LinkedTreeMap) json.get(key);
    }

    /**
     * @return the name of the pusher
     */
    public String getName(){ return namePusher; }

    /**
     * @return the e-mail address of the pusher
     */
    public String getEmail(){ return emailPusher; }

    /**
     * @return URL of the repository
     */
    public String getrepoURL(){ return repoURL; }

    /**
     * @return name of the repository
     */
    public String getrepoName(){ return repoName; }

    /**
     *
     * @return the commit ID of the push
     */
    public String getCommitID(){ return commitID; }

    /**
     * @return the branch who is pushing
     */
    public String getBranch(){ return repoBranch; }

}

