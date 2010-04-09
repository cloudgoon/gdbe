package com.google.gdbe.client.commands;

/**
 * Defines a command for undoing changes in the text editor.
 */
public class SystemUndoCommand extends Command {

  public final static int serialUid = 87;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public SystemUndoCommand() {
	super("Undo action.");
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
