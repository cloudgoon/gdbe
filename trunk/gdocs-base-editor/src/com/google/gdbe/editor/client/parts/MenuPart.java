package com.google.gdbe.editor.client.parts;

import com.google.gdbe.client.commands.Command;
import com.google.gdbe.client.commands.CurrentDocumentCopyCommand;
import com.google.gdbe.client.commands.CurrentDocumentDeleteCommand;
import com.google.gdbe.client.commands.CurrentDocumentRenameCommand;
import com.google.gdbe.client.commands.CurrentDocumentRevisionHistoryCommand;
import com.google.gdbe.client.commands.CurrentDocumentSaveCommand;
import com.google.gdbe.client.commands.ExistingDocumentOpenCommand;
import com.google.gdbe.client.commands.NewDocumentStartCommand;
import com.google.gdbe.client.commands.SystemAboutCommand;
import com.google.gdbe.client.commands.SystemOpenPageCommand;
import com.google.gdbe.client.commands.SystemRedoCommand;
import com.google.gdbe.client.commands.SystemToggleFullScreenCommand;
import com.google.gdbe.client.commands.SystemToggleLineNumbersCommand;
import com.google.gdbe.client.commands.SystemToggleSpellcheckCommand;
import com.google.gdbe.client.commands.SystemToggleWrapTextCommand;
import com.google.gdbe.client.commands.SystemUndoCommand;
import com.google.gdbe.client.commands.SystemUploadDocumentsCommand;
import com.google.gdbe.client.events.CommandEvent;
import com.google.gdbe.client.events.CommandHandler;
import com.google.gdbe.client.events.HasCommandHandlers;
import com.google.gdbe.client.resources.icons.Icons;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.AttachDetachException;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.HashMap;

/**
 * A specialized, non-reusable widget containing the main menu bar.
 */
public class MenuPart extends Composite implements HasCommandHandlers {
	
  /**
   * Extends the GWT MenuBar with a close method, for collapsing any open menus
   */
  private class MenuBarExt extends MenuBar {
    
	private boolean sim = false;
	
	/**
	 * @see MenuBar
	 */
	public MenuBarExt(boolean vertical) {
	  super(vertical);
	}
	
	/**
	 * Here we jump through some hoops just to cause the menu to close.
	 */
	public void close(){
      this.sim = true;
	  try {
	    this.onDetach();
	  } catch (AttachDetachException x) {
	  }
	  this.sim = false;
	}
	
	@Override
	public boolean isAttached() {
	  if (sim) {
		return false;
	  }
	  return super.isAttached();
	}
	
  }
  private HandlerManager manager;
  private MenuBarExt menu;
  
  private HashMap<String, MenuItem> itemIndex;
  
  /**
   * Constructs a Menu part.
   */
  public MenuPart() {
    manager = new HandlerManager(this);
    itemIndex = new HashMap<String, MenuItem>();
    VerticalPanel menuPanel = new VerticalPanel();
    menuPanel.setWidth("100%");
    menuPanel.setHeight("30px");
    menuPanel.add(buildMenu());
    menuPanel.setStylePrimaryName("gdbe-Menu-Panel");
    initWidget(menuPanel);
  }
  
  @Override
  public HandlerRegistration addCommandHandler(CommandHandler handler) {
	return manager.addHandler(CommandEvent.getType(), handler);
  }
  
  /**
   * Adds a menu item to a menu bar.
   * 
   * @param menuBar the menu bar to which to add the menu item
   * @param icon the icon image to use in the menu item
   * @param title title the title of the new menu item
   * @param command the command to execute when the new menu item is selected
   */
  private void addMenuItem(final MenuBarExt menuBar, AbstractImagePrototype icon, String title,
	  com.google.gwt.user.client.Command command) {
    MenuItem item = menuBar.addItem(icon.getHTML() + " " + title, true, command);
    itemIndex.put(title, item);
  }
  
  /**
   * Adds a menu item to a menu bar.
   * 
   * @param menuBar the menu bar to which to add the menu item
   * @param icon the icon image to use in the menu item
   * @param title the title of the new menu item
   * @param command the command type for the new menu item
   */
  private void addMenuItem(final MenuBarExt menuBar, AbstractImagePrototype icon, String title,
	  final Command command) {
    addMenuItem(menuBar, icon, title, new com.google.gwt.user.client.Command() {
      public void execute() {
    	CommandEvent.fire(MenuPart.this, command);
      }
    });
  }
  
  /**
   * Builds the main menu bar.
   * 
   * @return the main menu bar
   */
  private MenuBarExt buildMenu() {
    menu = new MenuBarExt(false);
    MenuBarExt fileMenu = new MenuBarExt(true);
    addMenuItem(fileMenu, Icons.editorIcons.Blank(), "New", new NewDocumentStartCommand());
    fileMenu.addSeparator();
    addMenuItem(fileMenu, Icons.editorIcons.Blank(), "Open", new ExistingDocumentOpenCommand());
    addMenuItem(fileMenu, Icons.editorIcons.Save(), "Save", new CurrentDocumentSaveCommand());
    addMenuItem(fileMenu, Icons.editorIcons.Blank(), "Save as new copy", new CurrentDocumentCopyCommand());
    addMenuItem(fileMenu, Icons.editorIcons.Blank(), "Rename...", new CurrentDocumentRenameCommand());
    addMenuItem(fileMenu, Icons.editorIcons.Blank(), "Delete...", new CurrentDocumentDeleteCommand());
    addMenuItem(fileMenu, Icons.editorIcons.Blank(), "Revision History", new CurrentDocumentRevisionHistoryCommand());
    fileMenu.addSeparator();
    addMenuItem(fileMenu, Icons.editorIcons.UploadDocument(), "Upload Files...", new SystemUploadDocumentsCommand());
    this.menu.addItem("File", fileMenu);
    MenuBarExt editMenu = new MenuBarExt(true);
    addMenuItem(editMenu, Icons.editorIcons.Undo(), "Undo", new SystemUndoCommand());
    addMenuItem(editMenu, Icons.editorIcons.Redo(), "Redo", new SystemRedoCommand());
    this.menu.addItem("Edit", editMenu);
    MenuBarExt viewMenu = new MenuBarExt(true);
    addMenuItem(viewMenu, Icons.editorIcons.CheckBlack(), "Check Spelling", new SystemToggleSpellcheckCommand());
    addMenuItem(viewMenu, Icons.editorIcons.CheckBlack(), "Wrap Text", new SystemToggleWrapTextCommand());
    addMenuItem(viewMenu, Icons.editorIcons.CheckBlack(), "Show Line Numbers", new SystemToggleLineNumbersCommand());
    viewMenu.addSeparator();
    addMenuItem(viewMenu, Icons.editorIcons.Blank(), "Full-screen mode", new SystemToggleFullScreenCommand());
    this.menu.addItem("View", viewMenu);
    MenuBarExt helpMenu = new MenuBarExt(true);
    addMenuItem(helpMenu, Icons.editorIcons.Blank(), "Submit bug or feature request", new SystemOpenPageCommand("IssueTracker", "http://code.google.com/p/gdbe/issues/entry"));
    helpMenu.addSeparator();
    addMenuItem(helpMenu, Icons.editorIcons.Blank(), "About", new SystemAboutCommand());
    this.menu.addItem("Help", helpMenu);
    return menu;
  }

  /**
   * Closes the menu.
   */
  public void close() {
	this.menu.close();
  }
  
  @Override
  public void fireEvent(GwtEvent<?> event) {
	manager.fireEvent(event);
  }

  /**
   * Retrieves the menu bar.
   * 
   * @return the menu bar
   */
  public MenuBar getMenuBar() {
	return this.menu;
  }
  
  /**
   * Retrieves a menu item by title.
   * 
   * @param title the title of the menu item which to retrieve
   * @return the menu item with the specified title
   */
  public MenuItem getMenuItem(String title) {
    return itemIndex.get(title);
  }
  
  /**
   * Sets the icon for a menu item, by title.
   * @param title the title of the menu item for which to set the icon image
   * @param icon the icon image
   */
  public void setMenuItemIcon(String title, AbstractImagePrototype icon) {
    MenuItem item = getMenuItem(title);
    item.setHTML(icon.getHTML() + " " + title);
  }
}
