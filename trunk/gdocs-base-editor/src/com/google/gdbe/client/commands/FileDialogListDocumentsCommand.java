package com.google.gdbe.client.commands;

/**
 * Defines a command for retrieving documents for use in the File Dialog.
 */
public class FileDialogListDocumentsCommand extends Command {

  public final static int serialUid = 34;
  
  private boolean useCache = true;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public FileDialogListDocumentsCommand(boolean useCache) {
    super("List documents.");
    this.useCache = useCache;
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
   * Retrieves whether to use cached documents, when available.
   * 
   * @return whether to use a document cache.
   */
  public boolean isUseCache() {
    return useCache;
  }
  
  /**
   * Sets whether to use cached documents, when available.
   * 
   * @param useCache whether to use a document cache.
   */
  public void setUseCache(boolean useCache) {
    this.useCache = useCache;
  }
  
}
