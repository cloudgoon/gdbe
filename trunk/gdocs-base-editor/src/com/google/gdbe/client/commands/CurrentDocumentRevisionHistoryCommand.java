package com.google.gdbe.client.commands;

/**
 * Defines a command for viewing the revision history of a given document.
 */
public class CurrentDocumentRevisionHistoryCommand extends Command {

  public final static int serialUid = 11;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public CurrentDocumentRevisionHistoryCommand() {
	super("View revision history.");
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
