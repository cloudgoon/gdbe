package com.google.gdbe.server;

import com.google.gdbe.server.auth.AuthenticationManager;
import com.google.gdbe.server.auth.AuthenticationTokenStore;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet corresponding to the document explorer.
 */
public class GdbeExplorerServlet extends HttpServlet {

  private static final long serialVersionUID = -7580011799692443138L;
  private static final Logger log = Logger.getLogger(GdbeExplorerServlet.class.getName());

  /**
   * Handles a GET request. Before writing out the html page, check for authentication
   * in passive mode. Passive mode means that, when unauthenticated, the user
   * will not be automatically redirected to the login or access control page and 
   * instead a splash page will be rendered which will allow the user to initiate
   * authentication.
   * In short, if the user has started authentication, continue with authentication,
   * otherwise display the splash page.
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws IOException {
    try {
	  AuthenticationManager authManager = new AuthenticationManager(
	      new AuthenticationTokenStore());
	  authManager.autoPilot(req, resp, true);
	  if (authManager.isAuthenticated()) {
	    outputFile(resp, "gdbe-explorer.html");
	  } else {
	    outputFile(resp, "gdbe-splash.html");
	  }
	} catch(Exception x) {
	  log.log(Level.SEVERE, "Auto-pilot authentication error: "  + x.getMessage(), x);
	}
  }
  
  /**
   * Handles a POST request. All POST requests initiate authentication, which redirects
   * the user first to the login page and then to the access control page as needed.
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws IOException {
    try {
  	  AuthenticationManager authManager = new AuthenticationManager(
  	      new AuthenticationTokenStore());
  	  authManager.autoPilot(req, resp, false);
      doGet(req, resp);
  	} catch(Exception x) {
  	  log.log(Level.SEVERE, "Auto-pilot authentication error: "  + x.getMessage(), x);
  	}
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
