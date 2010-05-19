package com.google.gdbe.client.commands;

/**
 * Defines a command for uploading documents.
 */
public class SystemUploadDocumentsCommand extends Command {

  public final static int serialUid = 97;

  /**
   * Creates a new command instance.
   */
  public SystemUploadDocumentsCommand() {
	super("Upload documents to your Google Docs account.");
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
