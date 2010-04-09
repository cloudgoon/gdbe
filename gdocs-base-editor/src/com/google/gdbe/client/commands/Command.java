package com.google.gdbe.client.commands;

/**
 * The base Command class.
 */
public abstract class Command {

  public final static int serialUid = 0;
	  
  protected String description;
  protected int attemptCount;
  
  /**
   * Creates a new command instance.
   * 
   * @param description the command description
   */
  public Command(String description) {
    this.description = description;
    this.attemptCount = 0;
  }
  
  /**
   * Retrieves the Command description.
   * 
   * @return the command description
   */
  public String getDescription() {
    return this.description;
  }

  /** 
   * Retrieves the number of times this command has been attempted.
   * 
   * @return the number of attempts
   */
  public int getAttemptCount() {
    return attemptCount;
  }

  /**
   * Increments the number of times this command has been attempted.
   */
  public void newAttempt() {
    this.attemptCount++;
  }

  /**
   * Retrieves the unique Command Id.
   * 
   * @return the unique Command Id
   */
  public abstract int getCommandId();
}
