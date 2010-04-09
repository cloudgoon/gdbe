package com.google.gdbe.client.commands;

/**
 * Defines a command for loading the contents of the current document.
 */
public class CurrentDocumentLoadContentsCommand extends Command {

  public final static int serialUid = 4;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public CurrentDocumentLoadContentsCommand() {
	super("Load current document contents.");
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
