package com.google.gdbe.editor.client.parts;

import com.google.gdbe.client.commands.Command;
import com.google.gdbe.client.commands.CurrentDocumentRenameCommand;
import com.google.gdbe.client.commands.CurrentDocumentSaveAndCloseCommand;
import com.google.gdbe.client.commands.CurrentDocumentSaveCommand;
import com.google.gdbe.client.commands.SystemSignOutCommand;
import com.google.gdbe.client.events.CommandEvent;
import com.google.gdbe.client.events.CommandHandler;
import com.google.gdbe.client.events.HasCommandHandlers;
import com.google.gdbe.client.resources.icons.Icons;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.Date;

/**
 * A specialized, non-reusable widget containing the logo, title, links and button menu.
 */
public class HeaderPart extends Composite implements HasCommandHandlers, ClickHandler {

  private HandlerManager manager;
  private VerticalPanel content;
  private FlexTable main;
  private Label author;
  private HorizontalPanel leftLinks, rightLinks, status;
  private Anchor title, signoutLink;
  private HTML info;
  
  /**
   * Constructs a new Header part.
   */
  public HeaderPart() {
	manager = new HandlerManager(this);
    content = new VerticalPanel();
    content.setWidth("100%");
    status = new HorizontalPanel();
    status.setWidth("100%");
    status.setStylePrimaryName("gdbe-Header-Status");
    status.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
    main = new FlexTable();
    main.setWidth("100%");
    main.setCellSpacing(0);
    main.insertRow(0);
    main.insertCell(0, 0);
    main.getFlexCellFormatter().setHeight(0, 0, "12");
    main.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
    main.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
    main.setWidth("100%");
    main.insertRow(1);
    main.insertCell(1, 0);
    main.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
    main.getFlexCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_LEFT);
    content.add(status);
    content.add(main);
    buildUpper();
    buildLower();
    initWidget(content);
  }
  
  @Override
  public HandlerRegistration addCommandHandler(CommandHandler handler) {
	return manager.addHandler(CommandEvent.getType(), handler);
  }
  
  /**
   * Builds a menu item.
   * 
   * @param menuBar the menu bar to which to add the item
   * @param icon the menu item's icon, if any
   * @param title the menu item's title
   * @param command the menu items associated command type
   * @return a menu item
   */
  private MenuItem addMenuItem(final MenuBar menuBar, AbstractImagePrototype icon, String title, final Command command) {
    MenuItem mi;
    if (icon == null) {
      mi = menuBar.addItem(title, true, (com.google.gwt.user.client.Command)null);
    } else {
      mi = menuBar.addItem(icon.getHTML() + " " + title, true, (com.google.gwt.user.client.Command)null);
    }
    mi.setCommand(new com.google.gwt.user.client.Command() {
      public void execute() {
      	CommandEvent.fire(HeaderPart.this, command);
      }
    });
    return mi;
  }
  
  /**
   * Builds the lower part of the editor.
   * The lower region contains the title, info and button menu controls.
   */
  private void buildLower() {
	FlexTable table = new FlexTable();
	table.setWidth("100%");
	table.setCellSpacing(0);
	table.setCellPadding(0);
	table.insertRow(0);
	table.insertCell(0, 0);
	table.insertCell(0, 1);
	table.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
    table.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	table.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
    table.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
    Image logo = Icons.editorIcons.Logo().createImage();
    logo.setStylePrimaryName("gdbe-Header-Logo");
    HorizontalPanel titlePanel = new HorizontalPanel();
    titlePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    titlePanel.getElement().getStyle().setOverflow(Overflow.HIDDEN);
    title = new Anchor("", "");
    title.setStylePrimaryName("gdbe-Header-Title");
    title.setTitle("Click to rename this document");
    title.addClickHandler(this);
    info = new HTML();
    info.setStylePrimaryName("gdbe-Header-Info");
    info.setHTML("not saved");
    titlePanel.add(logo);
    titlePanel.add(title);
    titlePanel.add(info);
    HorizontalPanel actionsPanel = new HorizontalPanel();
    actionsPanel.setHeight("30px");
    actionsPanel.setStylePrimaryName("gdbe-Header-Actions");
    MenuBar menu = new MenuBar(false);
    addMenuItem(menu, null, "Save", new CurrentDocumentSaveCommand());
    menu.addSeparator();
    addMenuItem(menu, null, "Save & Close", new CurrentDocumentSaveAndCloseCommand()).setStylePrimaryName("gdbe-HighlightedMenuItem");
    actionsPanel.add(menu);
    table.setWidget(0, 0, titlePanel);
    table.setWidget(0, 1, actionsPanel);
    main.setWidget(1, 0, table);
  }
  
  /**
   * Builds the upper part of the editor.
   * The upper region contains the Google Docs links, status and logo.
   */
  private void buildUpper() {
	FlexTable table = new FlexTable();
	table.setWidth("100%");
	table.setCellSpacing(0);
	table.setCellPadding(0);
	table.insertRow(0);
	table.insertCell(0, 0);
	table.insertCell(0, 1);
	table.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
    table.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	table.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
    table.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
    table.getFlexCellFormatter().setStylePrimaryName(0, 0, "gdbe-Header-Links");
    table.getFlexCellFormatter().setStylePrimaryName(0, 1, "gdbe-Header-Links");
    /* Upper Header Panel */
    leftLinks = new HorizontalPanel();
    Anchor projectLink = new Anchor("Project", "http://code.google.com/p/gdbe", "_blank");
    Anchor wikiLink = new Anchor("Wiki", "http://code.google.com/p/gdbe/w/list", "_blank");
    Anchor issuesLink = new Anchor("Issues", "http://code.google.com/p/gdbe/issues/list", "_blank");
    leftLinks.add(projectLink);
    leftLinks.add(wikiLink);
    leftLinks.add(issuesLink);
    rightLinks = new HorizontalPanel();
    author = new Label();
    Anchor docsLink = new Anchor("Docs Home", "http://docs.google.com/", "_blank");
    Anchor acLink = new Anchor("Google Access Control", "https://www.google.com/accounts/IssuedAuthSubTokens", "_blank");
    signoutLink = new Anchor("Sign Out");
    signoutLink.addClickHandler(new ClickHandler(){
      public void onClick(ClickEvent event) {
        CommandEvent.fire(HeaderPart.this, new SystemSignOutCommand("/splash.html"));
      }
    });
    rightLinks.add(author);
    rightLinks.add(docsLink);
    rightLinks.add(acLink);
    rightLinks.add(signoutLink);
    table.setWidget(0, 0, leftLinks);
    table.setWidget(0, 1, rightLinks);
    main.setWidget(0, 0, table);
  }
  
  @Override
  public void fireEvent(GwtEvent<?> event) {
	manager.fireEvent(event);
  }

  /**
   * Handles a click event. Listens to click events on the title region and prompts
   * for a rename by firing a rename command event.
   */
  @Override
  public void onClick(ClickEvent event) {
    if (event.getSource() == title) {
      CommandEvent.fire(HeaderPart.this, new CurrentDocumentRenameCommand());
      event.preventDefault();
    }
  }

  /**
   * Sets the displayed author.
   * 
   * @param author the author which to display
   */
  public void setAuthor(String author) {
    this.author.setText(author);
  }
  
  /**
   * Sets the displayed document info.
   * 
   * @param documentId the document id which to display
   * @param edited the date of last edit which to display
   * @param editor the editor which to display
   */
  public void setInfo(String documentId, Date edited, String editor) {
    info.setHTML(" saved on <a href=\"http://docs.google.com/Revs?id=" + documentId + "&tab=revlist\" target=\"_blank\">" +
        edited + "</a> by " + editor);
  }

  /**
   * Sets the contents of the status region.
   * 
   * @param status the status which to display
   */
  public void setStatus(String status) {
    this.status.clear();
    if (status != null & !status.equals("")) {
      this.status.add(new Label(status));
    }
  }
  
  /**
   * Sets the displayed title. If the title exceeds thirty characters it's truncated
   * and appended with ellipsis.
   * 
   * @param title the title which to display
   */
  public void setTitle(String title) {
    if (title.length() > 30) {
      title = title.substring(0, 30) + "...";
    }
    this.title.setText(title);
  }
  
}
