package com.google.gdbe.client.commands;

/**
 * Defines a command for redoing changes in the text editor.
 */
public class SystemRedoCommand extends Command {

  public final static int serialUid = 86;

  /**
   * Creates a new command instance.
   */
  public SystemRedoCommand() {
	super("Redo action.");
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
