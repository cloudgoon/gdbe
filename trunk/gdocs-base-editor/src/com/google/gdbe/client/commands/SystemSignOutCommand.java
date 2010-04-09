package com.google.gdbe.client.commands;

/**
 * Defines a command for signing out of the application.
 */
public class SystemSignOutCommand extends Command {

  public final static int serialUid = 24;
  
  private String returnUrl;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */  
  public SystemSignOutCommand(String returnUrl) {
	super("Sign out.");
	this.returnUrl = returnUrl;
  }

  /**
   * Retrieves the unique Command Id.
   * 
   * @return the unique Command Id
   */
  @Override
  public int getCommandId() {
    return serialUid;
  }

  /**
   * Retrieves the return url.
   * 
   * @return the return url
   */
  public String getReturnUrl() {
    return returnUrl;
  }

  /**
   * Sets the return url.
   * 
   * @param returnUrl the return url
   */
  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }
  
}
