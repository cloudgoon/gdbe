package com.google.gdbe.server;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdbe.server.auth.AuthenticationManager;
import com.google.gdbe.server.auth.AuthenticationTokenStore;

/**
 * The servlet corresponding to the document editor.
 */
public class GdbeEditorServlet extends HttpServlet {
  
  private static final long serialVersionUID = -1798829198884532452L;
  private static final Logger log = Logger.getLogger(GdbeEditorServlet.class.getName());

  /**
   * Handles a GET request. Before writing out the html page, check for authentication
   * in non-passive mode. Non-passive mode means that, when unauthenticated, the user
   * will be automatically redirected to the login or access control page. Passive mode
   * will only resume authentication if it's already in motion.
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws IOException {
	try {
	  AuthenticationManager authManager = new AuthenticationManager(
	      new AuthenticationTokenStore());
	  authManager.autoPilot(req, resp, false);
	} catch(Exception x) {
	  log.log(Level.SEVERE, "Auto-pilot authentication error: "  + x.getMessage(), x);
	}
	outputFile(resp, "gdbe-editor.html");
  }
  
  /**
   * Writes out a file to the client.
   * 
   * @param resp the HTTP response object
   * @param file the file which to write
   * @throws IOException if the file cannot be read or written
   */
  private void outputFile(HttpServletResponse resp, String file) throws IOException {
    resp.setContentType("text/html");
    char[] buffer = new char[1028];
    FileReader reader = new FileReader(file);
    int i = 0;
    while ((i = reader.read(buffer, 0, 1028)) > 0) {
      resp.getWriter().write(buffer, 0, i);
    }
  }
  
}
