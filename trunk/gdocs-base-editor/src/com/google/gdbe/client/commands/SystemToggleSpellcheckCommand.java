package com.google.gdbe.client.commands;

/**
 * Defines a command for toggling the spellchecker.
 */
public class SystemToggleSpellcheckCommand extends Command {

  public final static int serialUid = 111;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public SystemToggleSpellcheckCommand() {
	super("Update \"Use spellchecker\" setting.");
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
