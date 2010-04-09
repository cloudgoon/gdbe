package com.google.gdbe.client.commands;

/**
 * Defines a command for saving the current document.
 */
public class CurrentDocumentSaveCommand extends Command {

  public final static int serialUid = 7;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public CurrentDocumentSaveCommand() {
	super("Save current document.");
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
