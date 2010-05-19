package com.google.gdbe.client.commands;

/**
 * Defines a command for saving and closing the current document.
 */
public class CurrentDocumentSaveAndCloseCommand extends Command {

  public final static int serialUid = 6;

  /**
   * Creates a new command instance.
   */
  public CurrentDocumentSaveAndCloseCommand() {
	super("Save and close current document.");
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
