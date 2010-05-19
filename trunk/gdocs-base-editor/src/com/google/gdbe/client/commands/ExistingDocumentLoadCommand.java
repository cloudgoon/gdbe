package com.google.gdbe.client.commands;

/**
 * Defines a command for loading an existing document.
 */
public class ExistingDocumentLoadCommand extends Command {

  public final static int serialUid = 15;
  
  private String documentId;

  /**
   * Creates a new command instance.
   */
  public ExistingDocumentLoadCommand(String documentId) {
	super("Load existing document.");
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
   * Retrieves the Id of the document to be loaded.
   * 
   * @return the document Id
   */
  public String getDocumentId() {
    return documentId;
  }

  /**
   * Sets the Id of the document to be loaded.
   * 
   * @param documentId the document Id
   */
  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

}
