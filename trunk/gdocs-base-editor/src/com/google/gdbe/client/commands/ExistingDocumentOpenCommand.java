package com.google.gdbe.client.commands;

/**
 * Defines a command for opening an existing document.
 */
public class ExistingDocumentOpenCommand extends Command {

  public final static int serialUid = 16;
  
  /**
   * Creates a new command instance.
   */
  public ExistingDocumentOpenCommand() {
	super("Open existing document.");
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
