package buildtools;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.errors.GitAPIException;
import testTools.Tests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Continuous Integration server.
 */
public class CIServer extends AbstractHandler{
    private CloneRepo cr;
    private String tempFolderPath = System.getProperty("user.dir") + "/temp";

    /**
     * Handles the request by parsing the branch data. Compiling and
     * runs the branch tests to produce a test result. The result
     * will then be saved to the data base log history and the
     * pusher will be notified about the results.
     *
     * @param target the target project
     * @param baseRequest
     * @param request the request coming from the client to server
     * @param response the response to the client from the server
     * @throws IOException
     */
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);
        if("POST".equals(request.getMethod())){
            OutputStream out = new FileOutputStream(tempFolderPath + "/logs.txt");
            WebhookParser wh = new WebhookParser(request.getReader());
            try {
                cr = new CloneRepo(wh.getrepoURL(), wh.getBranch(), tempFolderPath);
            } catch (GitAPIException e) {
                e.printStackTrace();
            }
            File tempdir = cr.getTempDir();
            CompileFiles cf = null;
            try {
                cf = new CompileFiles(tempdir.toPath(), out);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            File errorFile = new File("errorFile");
            if(errorFile.length() == 0){
                System.out.println("Creating Tests object");
                try {
                    Tests tests = new Tests(tempdir);
                } catch (Exception e) {
                    System.out.println("Exception call");
                }
            }
            out.close();
            //SendMail(wh.getName(), wh.getEmail(), out, tests.getResults());
            System.out.println("Done!");
        }


        response.getWriter().println("CI job done");
    }

    /**
     * Used to start the CI server in command line
     * @param args Not used
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(8888);
        server.setHandler(new CIServer());
        server.start();
        server.join();
    }
}
