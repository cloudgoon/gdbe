package com.google.gdbe.client.commands;

/**
 * Defines a command for opening a page in a new browser window.
 */
public class SystemOpenPageCommand extends Command {

  public final static int serialUid = 105;
  
  private String name, url;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public SystemOpenPageCommand(String name, String url) {
	super("Open a webpage.");
	this.name = name;
	this.url = url;
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
   * Retrieves the name of the new browser window.
   * 
   * @return the name of the new browser window
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the url of the page to be opened.
   * 
   * @return the url of the page to be opened.
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets the name of the new browser window.
   * 
   * @param name the name of the new browser window
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the url of the page to be opened.
   * 
   * @param url the url of the page to be opened
   */
  public void setUrl(String url) {
    this.url = url;
  }

}
