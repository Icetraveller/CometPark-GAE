package com.cometpark;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuestbookServlet extends HttpServlet {
	 private static final Logger log = Logger.getLogger(SignGuestbookServlet.class.getName());
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
	  log.info("An informational message:"+req.toString());
    if (req.getParameter("testing") == null) {
      resp.setContentType("text/plain");
      resp.getWriter().println("Hello, this is a testing servlet. \n\n");
      Properties p = System.getProperties();
      p.list(resp.getWriter());

    } else {
      UserService userService = UserServiceFactory.getUserService();
      User currentUser = userService.getCurrentUser();

      if (currentUser != null) {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, " + currentUser.getNickname());
      } else {
        resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
      }
    }
  }
}