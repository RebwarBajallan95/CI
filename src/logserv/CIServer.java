package logserv;

import buildtools.CloneRepo;
import buildtools.CompileFiles;
import buildtools.SendMail;
import buildtools.WebhookParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.errors.GitAPIException;
import testTools.Tests;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CIServer extends AbstractHandler{

    private CloneRepo cr;
    private String tempFolderPath = System.getProperty("user.dir") + "/temp";

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
            // Listen to webhook endpoint
            if("POST".equals(request.getMethod())){
                // Output file where the logs are stored
                OutputStream out = new FileOutputStream(tempFolderPath + "/logs.txt");
                WebhookParser wh = new WebhookParser(request.getReader());
                try {
                    cr = new CloneRepo(wh.getrepoURL(), wh.getBranch(), tempFolderPath);
                } catch (GitAPIException e) {
                    e.printStackTrace();
                }
                File tempdir = cr.getTempDir();
                // Compile files
                CompileFiles cf = new CompileFiles(tempdir.toPath(), out);
                if(cf.compileStatus() == 1){
                    Tests tests = new Tests(tempdir.getPath(), out);
                }
                out.close();
                // Send email
                SendMail(wh.getName(), wh.getEmail(), out, tests.getResults());
                // Enter results into DB
                System.out.println("Done!");
            }


            response.getWriter().println("CI job done");
        }



        // Used to start the CI server in command line
        public static void main(String[] args) throws Exception
        {

            Server server = new Server(8888);
            server.setHandler(new CIServer());
            server.start();
            server.join();
        }
}
