package com.google.gdbe.server.auth;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Exposes methods for retrieving and storing Authentication Tokens.
 */
public class AuthenticationTokenStore {

  /**
   * Retrieves the Authentication Token for the current user, or null if none.
   * 
   * @return the Authentication Token for the current user, or null if none
   */
  public AuthenticationToken getUserToken() {
    UserService userService = UserServiceFactory.getUserService();
    if (userService.getCurrentUser() != null) {
      String email = userService.getCurrentUser().getEmail();
	  return AuthenticationToken.getUserToken(email);
    }
    return null;
  }
  
  /**
   * Retrieves the Authentication Token for the specified user, or null if none.
   * 
   * @param email the user's email
   * @return the Authentication Token for the specified user, or null if none
   */
  public AuthenticationToken getUserToken(String email) {
	return AuthenticationToken.getUserToken(email);
  }

  /**
   * Sets the Authentication Token for a given user.
   * 
   * @param email the user's email
   * @param token the user's Authentication Token
   */
  public void setUserToken(String email, String token) {
	AuthenticationToken.storeUserToken(email, token);
  }

}
