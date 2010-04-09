package com.google.gdbe.client.gdocs;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A document user.
 */
public class DocumentUser implements IsSerializable {

  private String id, name, email;

  /**
   * Constructs a new Document User.
   */
  public DocumentUser() { }

  /**
   * Retrieves the user's email address.
   * @return the user's email address
   */
  public String getEmail() {
    return email;
  }

  /**
   * Retrieves the user's id.
   * @return the user's id
   */
  public String getId() {
    return id;
  }

  /**
   * Retrieves the user's name.
   * @return the user's name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the user's email address.
   * @param email the user's email address
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Sets the user's id.
   * @param id the user's id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Sets the user's name
   * @param name the user's name
   */
  public void setName(String name) {
    this.name = name;
  }
  
}
