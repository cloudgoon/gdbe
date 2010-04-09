package com.google.gdbe.client.commands;

/**
 * Defines a command for starting a new document.
 */
public class NewDocumentStartCommand extends Command {

  public final static int serialUid = 23;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public NewDocumentStartCommand() {
	super("Start new document.");
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