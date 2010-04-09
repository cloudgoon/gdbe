package com.google.gdbe.client.commands;

/**
 * Defines a command for creating a copy of a document.
 */
public class CurrentDocumentCopyCommand extends Command {

  public final static int serialUid = 2;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public CurrentDocumentCopyCommand() {
	super("Save current document as new copy.");
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
