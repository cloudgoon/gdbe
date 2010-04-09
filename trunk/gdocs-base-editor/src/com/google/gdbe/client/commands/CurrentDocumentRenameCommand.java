package com.google.gdbe.client.commands;

/**
 * Defines a command for renaming the current document.
 */
public class CurrentDocumentRenameCommand extends Command {

  public final static int serialUid = 5;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public CurrentDocumentRenameCommand() {
	super("Rename current document.");
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
