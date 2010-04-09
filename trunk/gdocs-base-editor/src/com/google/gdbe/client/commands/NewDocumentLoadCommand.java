package com.google.gdbe.client.commands;

/**
 * Defines a command for loading a new document.
 */
public class NewDocumentLoadCommand extends Command {

  public final static int serialUid = 22;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public NewDocumentLoadCommand() {
	super("Load new document.");
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
