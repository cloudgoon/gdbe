package com.google.gdbe.client.commands;

public class CurrentDocumentRefreshCommand extends Command {

  public final static int serialUid = 113;
  
  protected boolean executeInBackground = true;
  protected Command continueCommand;

  /**
   * Creates a new command instance.
   */
  public CurrentDocumentRefreshCommand(boolean executeInBackground,
	    Command continueCommand) {
	super("Refresh current document.");
	this.executeInBackground = executeInBackground;
	this.continueCommand = continueCommand;
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
   * Retrieves the command to execute upon refresh.
   * 
   * @return the command to execute upon refresh
   */
  public Command getContinueCommand() {
    return continueCommand;
  }

  /**
   * Sets the command to execute upon refresh.
   * 
   * @param continueCommand the command to execute upon refresh
   */
  public void setContinueCommand(Command continueCommand) {
    this.continueCommand = continueCommand;
  }

  /**
   * Retrieves whether the refresh should execute in the background.
   * 
   * @return whether the refresh should execute in the background.
   */
  public boolean isExecuteInBackground() {
    return executeInBackground;
  }

  /**
   * Sets whether the refresh should execute in the background.
   * 
   * @param executeInBackground whether the refresh should execute in
   * the background.
   */
  public void setExecuteInBackground(boolean executeInBackground) {
    this.executeInBackground = executeInBackground;
  }
 
}
