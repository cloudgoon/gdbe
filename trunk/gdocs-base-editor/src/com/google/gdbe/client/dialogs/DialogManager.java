package com.google.gdbe.client.dialogs;

public class DialogManager {
	
  private Dialog activeDialog;

  /**
   * Constructs a new Dialog Manager.
   */
  public DialogManager() {
  }
  
  /**
   * Hides a given dialog.
   */
  public void hideDialog() {
    if (activeDialog != null) {
      activeDialog.hide();
      activeDialog = null;
    }
  }
  
  /**
   * Shows a given dialog.
   * @param dialog
   */
  public void showDialog(Dialog dialog) {
    activeDialog = dialog;
    activeDialog.center();
  }
  
}
