package com.google.gdbe.client.commands;

/**
 * Defines a command for toggling text wrapping.
 */
public class SystemToggleWrapTextCommand extends Command {

  public final static int serialUid = 100;

  /**
   * Creates a new command instance.
   */
  public SystemToggleWrapTextCommand() {
	super("Update \"Wrap Text\" setting.");
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
