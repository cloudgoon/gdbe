package com.google.gdbe.client.commands;

/**
 * Defines a command for opening a document from the File Dialog.
 */
public class FileDialogOpenDocumentCommand extends Command {

  public final static int serialUid = 35;
  
  private String documentId;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public FileDialogOpenDocumentCommand(String documentId) {
    super("Open document.");
    this.documentId = documentId;
  }

  /**
   * Retrieves the Id of the document to be opened.
   * 
   * @return the Id of the document to be opened
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Sets the Id of the document to be opened.
   * 
   * @param documentId the Id of the document to be opened
   */
  public void setDocumentId(String documentId) {
    this.documentId = documentId;
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
