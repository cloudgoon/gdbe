package com.google.gdbe.client.commands;

/**
 * Defines a command for retrieving the contents of a folder, for use in the File Dialog.
 */
public class FileDialogOpenFolderCommand extends Command{

  public final static int serialUid = 35;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public FileDialogOpenFolderCommand() {
    super("Open document.");
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
