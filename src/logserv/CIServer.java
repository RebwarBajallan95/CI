package logserv;

import buildtools.CloneRepo;
import buildtools.WebhookParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CIServer extends AbstractHandler{

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
                WebhookParser wh = new WebhookParser(request.getReader());
                try {
                    new CloneRepo(wh.getrepoURL(), wh.getBranch(), "./temp");
                } catch (GitAPIException e) {
                    e.printStackTrace();
                }
                // Compile files

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
