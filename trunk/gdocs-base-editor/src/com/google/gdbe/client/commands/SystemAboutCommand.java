package com.google.gdbe.client.commands;

/**
 * Defines a command for displaying the About Dialog.
 */
public class SystemAboutCommand extends Command {

  public final static int serialUid = 12;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public SystemAboutCommand() {
	super("About Cloudie.");
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

}
