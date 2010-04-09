package com.google.gdbe.client.commands;

/**
 * Defines a command for toggling line numbers.
 */
public class SystemToggleLineNumbersCommand extends Command {

  public final static int serialUid = 101;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public SystemToggleLineNumbersCommand() {
	super("Update \"Show Line Numbers\" setting.");
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
