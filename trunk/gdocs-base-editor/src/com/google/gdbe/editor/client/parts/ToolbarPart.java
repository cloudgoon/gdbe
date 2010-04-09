package com.google.gdbe.editor.client.parts;

import java.util.ArrayList;

import com.google.gdbe.client.commands.Command;
import com.google.gdbe.client.commands.CurrentDocumentSaveCommand;
import com.google.gdbe.client.commands.ExistingDocumentOpenCommand;
import com.google.gdbe.client.commands.SystemRedoCommand;
import com.google.gdbe.client.commands.SystemUndoCommand;
import com.google.gdbe.client.events.CommandEvent;
import com.google.gdbe.client.events.CommandHandler;
import com.google.gdbe.client.events.HasCommandHandlers;
import com.google.gdbe.client.resources.icons.Icons;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * A specialized, non-reusable widget containing the main toolbar.
 */
public class ToolbarPart extends Composite implements HasCommandHandlers {

  private HandlerManager manager;
  private HorizontalPanel bar;
  private ArrayList<Widget> buttons;
  
  /**
   * Constructs a Toolbar part.
   */
  public ToolbarPart() {
	manager = new HandlerManager(this);
	buttons = new ArrayList<Widget>();
    bar = new HorizontalPanel();
    bar.setHeight("30px");
    bar.setWidth("100%");
    bar.setStylePrimaryName("gdbe-Tools-Panel");
    bar.add(buildToolBar());
    initWidget(bar);
  }
  
  @Override
  public HandlerRegistration addCommandHandler(CommandHandler handler) {
	return manager.addHandler(CommandEvent.getType(), handler);
  }
  
  /**
   * Builds a toolbar button.
   * 
   * @param icon the button's icon
   * @param title the button's tooltip text
   * @param isToggle whether the button is of type toggle or push
   * @param command the command type for the toolbar button
   * @return a toolbar button
   */
  private Widget buildButton(AbstractImagePrototype icon, String title, boolean isToggle, final Command command){
    if(isToggle){
      final ToggleButton btn = new ToggleButton(icon.createImage());
      btn.setTitle(title);
      btn.addClickHandler(new ClickHandler(){
        public void onClick(ClickEvent event) {
          btn.setFocus(false);
          btn.removeStyleName("gwt-ToggleButton-up-hovering");
          CommandEvent.fire(ToolbarPart.this, command);
        }
      });
      buttons.add(btn);
      return btn;
    }else{
      final PushButton btn = new PushButton(icon.createImage());
      btn.setTitle(title);
      btn.addClickHandler(new ClickHandler(){
        public void onClick(ClickEvent event) {
          btn.setFocus(false);
          btn.removeStyleName("gwt-PushButton-up-hovering");
          CommandEvent.fire(ToolbarPart.this, command);
        }
      });
      buttons.add(btn);
      return btn;
    }
  }
  
  /**
   * Builds a separator widget.
   * 
   * @return a separator widget
   */
  private Image buildSeparator() {
	Image sep = Icons.editorIcons.Separator().createImage();
	sep.addStyleName("separator");
    return sep;
  }
  
  /**
   * Builds the main toolbar.
   * 
   * @return the main toolbar
   */
  private HorizontalPanel buildToolBar() {
    HorizontalPanel toolbarPanel = new HorizontalPanel();
    toolbarPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    toolbarPanel.setStyleName("gdbe-Toolbar");
    toolbarPanel.add(buildButton(Icons.editorIcons.OpenDocument(), "Open Document", false, new ExistingDocumentOpenCommand()));
    toolbarPanel.add(buildButton(Icons.editorIcons.Save(), "Save", false, new CurrentDocumentSaveCommand()));
    toolbarPanel.add(buildSeparator());
    toolbarPanel.add(buildButton(Icons.editorIcons.Undo(), "Undo", false, new SystemUndoCommand()));
    toolbarPanel.add(buildButton(Icons.editorIcons.Redo(), "Redo", false, new SystemRedoCommand()));
    return toolbarPanel;
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
	manager.fireEvent(event);
  }
  
  /**
   * Sets the toggle state for a given toolbar buttom.
   * 
   * @param index the index of the button to be updated
   * @param down whether the new state should be the down state
   */
  public void setButtonState(int index, boolean down) {
    Widget btn = buttons.get(index);
    if (btn instanceof ToggleButton) {
    	ToggleButton tbtn = (ToggleButton) btn;
    	tbtn.setDown(down);
    }
  }

}
