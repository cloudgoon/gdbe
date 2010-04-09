package com.google.gdbe.client.commands;

/**
 * Defines a command for toggling full-screen mode.
 */
public class SystemToggleFullScreenCommand extends Command {

  public final static int serialUid = 10;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public SystemToggleFullScreenCommand() {
	super("Display editor in full screen view.");
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
