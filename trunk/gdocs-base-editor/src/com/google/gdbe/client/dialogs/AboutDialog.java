package com.google.gdbe.client.dialogs;

import com.google.gdbe.client.events.CommandHandler;
import com.google.gdbe.client.resources.icons.Icons;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A dialog window displaying details of the application.
 */
public class AboutDialog extends Dialog {

  protected static AboutDialog instance;
  
  /**
   * Retrieves the common instance of this dialog.
   * @param handler the command handler
   * @return the common instance of this dialog
   */
  public static AboutDialog get(CommandHandler handler) {
    if (instance == null) {
      instance = new AboutDialog();
      instance.addCommandHandler(handler);
    }
    return instance;
  }

  /**
   * Constructs an About dialog window.
   */
  public AboutDialog() {
    super("About", true);
    addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        hide();
      }
    });
    VerticalPanel content = new VerticalPanel();
    content.setStylePrimaryName("gdbe-About");
    Image logo = Icons.editorIcons.LogoLarge().createImage();
    content.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    content.add(logo);
    content.setSize("500px", "100px");
    HTML l1 = new HTML("&copy; 2010 GDBE Project (1.2). Licensed under the Apache License, Version 2.0.");
    l1.setStylePrimaryName("gdbe-About-Caption");
    content.add(l1);
    setContentWidget(content);
  }
}
