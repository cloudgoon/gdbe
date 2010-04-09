package com.google.gdbe.client.commands;

/**
 * Defines a command for unstarring a document, from the File Dialog.
 */
public class FileDialogUnstarDocumentCommand extends Command {

  public final static int serialUid = 31;
  
  private String documentId;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public FileDialogUnstarDocumentCommand(String documentId) {
    super("Unstar document.");
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

  /**
   * Retrieves the Id of the document to be unstarred.
   * 
   * @return the Id of the document to be unstarred
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Sets the Id of the document to be unstarred.
   * 
   * @param documentId the Id of the document to be unstarred
   */
  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

}
