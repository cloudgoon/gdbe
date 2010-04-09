package com.google.gdbe.client.commands;

/**
 * Defines a command for starring a document, from the File Dialog.
 */
public class FileDialogStarDocumentCommand extends Command {

  public final static int serialUid = 32;
  
  private String documentId;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public FileDialogStarDocumentCommand(String documentId) {
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
   * Retrieves the Id of the document to be starred.
   * 
   * @return the Id of the document to be starred
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Sets the Id of the document to be starred.
   * 
   * @param documentId the Id of the document to be starred
   */
  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

}
