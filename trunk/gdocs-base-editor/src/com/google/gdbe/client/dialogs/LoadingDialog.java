package com.google.gdbe.client.dialogs;

import com.google.gdbe.client.events.CommandHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A dialog window displaying a loading message.
 */
public class LoadingDialog extends Dialog {

  protected static LoadingDialog instance;

  /**
   * Retrieves the common instance of this dialog.
   * 
   * @param handler the command handler
   * @return the common instance of this dialog
   */
  public static LoadingDialog get(CommandHandler handler) {
    if (instance == null) {
      instance = new LoadingDialog();
      instance.addCommandHandler(handler);
    }
    return instance;
  }
  
  protected Label message;
  
  /**
   * Constructs a Loading dialog window.
   */
  protected LoadingDialog() {
    super("Loading", true);
    closeButton.setVisible(false);
    message = new Label();
    VerticalPanel content = new VerticalPanel();
    content.setWidth("500px");
    content.add(message);
    setContentWidget(content);
  }
  
  /**
   * Centers and displays the dialog window and sets the dialog window's message.
   * 
   * @param msg
   */
  public void center(String msg) {
    super.center();
    setMessage(msg);
  }
  
  /**
   * Retrieves the dialog window's message.
   * 
   * @return the dialog window's message
   */
  public String getMessage() {
    return message.getText();
  }
  
  /**
   * Sets the dialog window's message.
   * 
   * @param msg
   */
  public void setMessage(String msg) {
    message.setText(msg);
  }
}
