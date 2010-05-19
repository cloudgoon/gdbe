package com.google.gdbe.client.commands;

/**
 * Defines a command for deleting a document.
 */
public class CurrentDocumentDeleteCommand extends Command {

  public final static int serialUid = 3;

  /**
   * Creates a new command instance.
   */
  public CurrentDocumentDeleteCommand() {
	super("Delete current document.");
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