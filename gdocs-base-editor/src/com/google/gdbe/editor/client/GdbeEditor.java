package com.google.gdbe.editor.client;

import com.google.gdbe.client.commands.Command;
import com.google.gdbe.client.commands.CurrentDocumentCopyCommand;
import com.google.gdbe.client.commands.CurrentDocumentDeleteCommand;
import com.google.gdbe.client.commands.CurrentDocumentLoadContentsCommand;
import com.google.gdbe.client.commands.CurrentDocumentRefreshCommand;
import com.google.gdbe.client.commands.CurrentDocumentRenameCommand;
import com.google.gdbe.client.commands.CurrentDocumentRevisionHistoryCommand;
import com.google.gdbe.client.commands.CurrentDocumentSaveAndCloseCommand;
import com.google.gdbe.client.commands.CurrentDocumentSaveCommand;
import com.google.gdbe.client.commands.ExistingDocumentLoadCommand;
import com.google.gdbe.client.commands.ExistingDocumentOpenCommand;
import com.google.gdbe.client.commands.FileDialogListDocumentsCommand;
import com.google.gdbe.client.commands.FileDialogOpenDocumentCommand;
import com.google.gdbe.client.commands.FileDialogStarDocumentCommand;
import com.google.gdbe.client.commands.FileDialogUnstarDocumentCommand;
import com.google.gdbe.client.commands.NewDocumentLoadCommand;
import com.google.gdbe.client.commands.NewDocumentStartCommand;
import com.google.gdbe.client.commands.SystemAboutCommand;
import com.google.gdbe.client.commands.SystemOpenPageCommand;
import com.google.gdbe.client.commands.SystemRedoCommand;
import com.google.gdbe.client.commands.SystemSignOutCommand;
import com.google.gdbe.client.commands.SystemToggleFullScreenCommand;
import com.google.gdbe.client.commands.SystemToggleLineNumbersCommand;
import com.google.gdbe.client.commands.SystemToggleSpellcheckCommand;
import com.google.gdbe.client.commands.SystemToggleWrapTextCommand;
import com.google.gdbe.client.commands.SystemUndoCommand;
import com.google.gdbe.client.commands.SystemUploadDocumentsCommand;
import com.google.gdbe.client.dialogs.AboutDialog;
import com.google.gdbe.client.dialogs.DialogManager;
import com.google.gdbe.client.dialogs.ErrorDialog;
import com.google.gdbe.client.dialogs.FileListDialog;
import com.google.gdbe.client.dialogs.LoadingDialog;
import com.google.gdbe.client.events.CommandEvent;
import com.google.gdbe.client.events.CommandHandler;
import com.google.gdbe.client.events.IntRunnable;
import com.google.gdbe.client.gdocs.DocumentService;
import com.google.gdbe.client.gdocs.DocumentServiceAsync;
import com.google.gdbe.client.gdocs.DocumentServiceEntry;
import com.google.gdbe.client.gdocs.DocumentUser;
import com.google.gdbe.client.resources.icons.Icons;
import com.google.gdbe.editor.client.parts.EditorPart;
import com.google.gdbe.editor.client.parts.HeaderPart;
import com.google.gdbe.editor.client.parts.MenuPart;
import com.google.gdbe.editor.client.parts.ToolbarPart;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.Date;

/**
 * The GDBE document editor module.
 */
public class GdbeEditor implements EntryPoint, CommandHandler {

  private final DocumentServiceAsync docService = GWT.create(DocumentService.class);
  private AbsolutePanel root;
  private DialogManager dialogManager;
  private EditorPart editor;
  private FlexTable contentPane;
  private HeaderPart header;
  private MenuPart menu;
  private ToolbarPart toolbar;
  private DocumentUser currentUser;
  private DocumentServiceEntry currentDocument;
  private DocumentServiceEntry[] allDocuments;
  private IntRunnable controlKeyHandler;

  /**
   * Clears and hides any visible status messages.
   */
  private void clearStatus() {
	LoadingDialog.get(this).hide();
    header.setStatus("");
  }
  
  /**
   * Closes the current window.
   */
  private native void close() /*-{
  	$wnd.open('', '_self', '');//for chrome
    $wnd.close();
  }-*/;
  
  /**
   * Handles a command event. This is where all the components converge along with
   * the application logic.
   * 
   * @param the command event
   */
  public <T extends Command> void execute(final T cmd) {
    Date now = new Date();
    switch (cmd.getCommandId()) {
      case FileDialogUnstarDocumentCommand.serialUid:
    	FileDialogUnstarDocumentCommand fdudCmd = ((FileDialogUnstarDocumentCommand)cmd);
	    docService.setDocumentStarred(fdudCmd.getDocumentId(),
	        false,
	        new AsyncCallback<Boolean>(){
	          public void onFailure(Throwable caught) {
	            handleError(caught, cmd, null, 1);
	          }
	          public void onSuccess(Boolean result) {
	          }
	    });
	    break;
      case FileDialogStarDocumentCommand.serialUid:
    	FileDialogStarDocumentCommand fdsdCmd = ((FileDialogStarDocumentCommand)cmd);
        docService.setDocumentStarred(
            fdsdCmd.getDocumentId(),
            true,
            new AsyncCallback<Boolean>(){
              public void onFailure(Throwable caught) {
                handleError(caught, cmd, null, 1);
              }
              public void onSuccess(Boolean result) {
              }
            }
        );
        break;
      case FileDialogListDocumentsCommand.serialUid:
    	FileDialogListDocumentsCommand fdldCmd = (FileDialogListDocumentsCommand) cmd;
    	if (fdldCmd.isUseCache() && allDocuments != null) {
    	  FileListDialog fileListDialog = FileListDialog.get(GdbeEditor.this);
          fileListDialog.setEntries(allDocuments);
          fileListDialog.showEntries();
    	} else {
          docService.getDocuments(false, new AsyncCallback<DocumentServiceEntry[]>() {
            public void onFailure(Throwable caught) {
              handleError(caught, cmd, null, 1);
            }
            public void onSuccess(DocumentServiceEntry[] result) {
              allDocuments = result;
        	  FileListDialog fileListDialog = FileListDialog.get(GdbeEditor.this);
              fileListDialog.setEntries(result);
              fileListDialog.showEntries();
            }
          });
    	}
        break;
      case FileDialogOpenDocumentCommand.serialUid:
    	FileDialogOpenDocumentCommand fdodCmd = (FileDialogOpenDocumentCommand) cmd;
        Window.open("http://docs.google.com/Doc?docid=" + fdodCmd.getDocumentId() + "&hl=en", fdodCmd.getDocumentId(), "");
        break;
      case NewDocumentStartCommand.serialUid:
        Window.open("/docs", "Untitled" + now.getTime(), "");
        break;
      case ExistingDocumentOpenCommand.serialUid:
    	dialogManager.showDialog(FileListDialog.get(this));
        break;
      case CurrentDocumentCopyCommand.serialUid:
        showStatus("Copying document...", true);
        docService.createDocument("Copy of " + currentDocument.getTitle(), editor.getText(),
            new AsyncCallback<DocumentServiceEntry>(){
              public void onFailure(Throwable caught) {
                handleError(caught, cmd, null, 0);
              }
              public void onSuccess(DocumentServiceEntry result) {
                clearStatus();
                Window.open("/docs?docid=" + result.getDocumentId(), result.getDocumentId(), "");
              }
        });
        break;
      case CurrentDocumentSaveCommand.serialUid:
        showStatus("Saving...", false);
        docService.saveDocument(currentDocument.getDocumentId(), currentDocument.getEtag(), currentDocument.getTitle(),
            editor.getText(), new AsyncCallback<DocumentServiceEntry>() {
          public void onFailure(Throwable caught) {
            handleError(caught, cmd, null, 0);
          }
          public void onSuccess(DocumentServiceEntry result) {
            setDocument(result, true);
            clearStatus();
          }
        });
        break;
      case CurrentDocumentSaveAndCloseCommand.serialUid:
        showStatus("Saving document...", true);
        docService.saveDocument(currentDocument.getDocumentId(), currentDocument.getEtag(), currentDocument.getTitle(),
        	editor.getText(), new AsyncCallback<DocumentServiceEntry>() {
          public void onFailure(Throwable caught) {
            handleError(caught, cmd, null, 0);
          }
          public void onSuccess(DocumentServiceEntry result) {
            setDocument(result, false);
            clearStatus();
            close();
          }
        });
        break;
      case CurrentDocumentRenameCommand.serialUid:
        final String newName = Window.prompt("Enter new document name:", currentDocument.getTitle());
        if (newName != null && !newName.equals("")) {
          if (currentDocument.isStored()) {
            showStatus("Renaming...", false);
            docService.renameDocument(currentDocument.getDocumentId(), newName,
                new AsyncCallback<DocumentServiceEntry>(){
              public void onFailure(Throwable caught) {
                handleError(caught, cmd, null, 0);
              }
              public void onSuccess(DocumentServiceEntry result) {
                setDocument(result, true);
                clearStatus();
              }
            });
          } else {
            currentDocument.setTitle(newName);
            setDocument(currentDocument, false);
          }
        }
        break;
      case CurrentDocumentDeleteCommand.serialUid:
        if (Window.confirm("This document will be deleted and closed.")) {
          if (currentDocument.isStored()) {
            showStatus("Deleting document...", true);
            docService.deleteDocument(currentDocument.getDocumentId(), "*",
                  new AsyncCallback<Boolean>() {
              public void onFailure(Throwable caught) {
                handleError(caught, cmd, null, 0);
              }
              public void onSuccess(Boolean result) {
                clearStatus();
                close();
              }
            });
          } else {
            close();
          }
        }
        break;
      case CurrentDocumentLoadContentsCommand.serialUid:
        showStatus("Loading document contents...", true);
        docService.getDocumentContents(currentDocument.getDocumentId(),
            new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            handleError(caught, cmd, null, 1);
          }
          public void onSuccess(String result) {
            clearStatus();
            loadEditor(result);
          }
        });
        break;
      case CurrentDocumentRefreshCommand.serialUid:
      	final CurrentDocumentRefreshCommand cdrCmd = (CurrentDocumentRefreshCommand) cmd;
          if (!cdrCmd.isExecuteInBackground()) {
      	    showStatus("Obtaining document metadata...", true);
          }
          docService.getDocument(currentDocument.getDocumentId(), new AsyncCallback<DocumentServiceEntry>() {
            public void onFailure(Throwable caught) {
          	  if (!cdrCmd.isExecuteInBackground()) {
                handleError(caught, cmd, null, 1);
          	  }
            }
            public void onSuccess(DocumentServiceEntry result) {
              setDocument(result, false);
              if (!cdrCmd.isExecuteInBackground()) {
                clearStatus();
              }
              if (cdrCmd.getContinueCommand() != null) {
                execute(cdrCmd.getContinueCommand());
              }
            }
          });
      	break;
      case CurrentDocumentRevisionHistoryCommand.serialUid:
        Window.open("http://docs.google.com/Revs?id=" +
            currentDocument.getDocumentId() + "&tab=revlist",
            currentDocument.getDocumentId(), "");
        break;
      case SystemUploadDocumentsCommand.serialUid:
        Window.open("http://docs.google.com/DocAction?action=updoc&hl=en",
            "UploadDocuments", "");
        break;
      case NewDocumentLoadCommand.serialUid:
    	docService.getNewDocument(new AsyncCallback<DocumentServiceEntry>() {
			@Override
			public void onFailure(Throwable caught) {
              handleError(caught, cmd, null, 1);
			}
			@Override
			public void onSuccess(DocumentServiceEntry result) {
		      setDocument(result, false);
		      loadEditor("Hello GDBE!!");
		    }
    	});
        break;
      case ExistingDocumentLoadCommand.serialUid:
    	final ExistingDocumentLoadCommand edlCmd = (ExistingDocumentLoadCommand) cmd;
        showStatus("Loading document...", true);
        docService.getDocument(edlCmd.getDocumentId(), new AsyncCallback<DocumentServiceEntry>() {
          public void onFailure(Throwable caught) {
            handleError(caught, cmd, new NewDocumentLoadCommand(), 1);
          }
          public void onSuccess(DocumentServiceEntry result) {
        	if (result == null) {
        	  handleError(new Exception("No document found with the ID " + edlCmd.getDocumentId()), cmd, new NewDocumentLoadCommand(), 0);
        	} else {
              setDocument(result, false);
              clearStatus();
              execute(new CurrentDocumentLoadContentsCommand());
        	}
          }
        });
        break;
      case SystemAboutCommand.serialUid:
    	dialogManager.showDialog(AboutDialog.get(this));
        break;
      case SystemToggleFullScreenCommand.serialUid:
        boolean isFullScreen = !header.isVisible();
        if (isFullScreen) {
          menu.setMenuItemIcon("Full-screen mode", Icons.editorIcons.Blank());
          header.setVisible(true);
          contentPane.getFlexCellFormatter().setHeight(0, 0, "120px");
        } else {
          menu.setMenuItemIcon("Full-screen mode", Icons.editorIcons.CheckBlack());
          header.setVisible(false);
          contentPane.getFlexCellFormatter().setHeight(0, 0, "40px");
        }
        break;
      case SystemToggleLineNumbersCommand.serialUid:
    	if (editor.getShowLineNumbers()) {
    	  menu.setMenuItemIcon("Show Line Numbers", Icons.editorIcons.Blank());
    	  editor.setShowLineNumbers(false);
    	} else {
    	  menu.setMenuItemIcon("Show Line Numbers", Icons.editorIcons.CheckBlack());
    	  editor.setShowLineNumbers(true);
    	}
    	break;
      case SystemToggleWrapTextCommand.serialUid:
    	if (editor.getWrapText()) {
    	  menu.setMenuItemIcon("Wrap Text", Icons.editorIcons.Blank());
    	  editor.setWrapText(false);
    	} else {
    	  menu.setMenuItemIcon("Wrap Text", Icons.editorIcons.CheckBlack());
    	  editor.setWrapText(true);
    	}
    	break;
      case SystemToggleSpellcheckCommand.serialUid:
      	if (editor.getUseSpellChecker()) {
      	  menu.setMenuItemIcon("Check Spelling", Icons.editorIcons.Blank());
      	  editor.setUseSpellChecker(false);
      	} else {
      	  menu.setMenuItemIcon("Check Spelling", Icons.editorIcons.CheckBlack());
      	  editor.setUseSpellChecker(true);
      	}
      	break;
      case SystemSignOutCommand.serialUid:
        showStatus("Signing out...", true);
        docService.logout(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
	          handleError(caught, cmd, null, 1);
			}
			@Override
			public void onSuccess(String result) {
              clearStatus();
              Window.Location.replace(((SystemSignOutCommand)cmd).getReturnUrl());
			}
        });
        break;
      case SystemUndoCommand.serialUid:
    	editor.undo();
    	break;
      case SystemRedoCommand.serialUid:
    	editor.redo();
    	break;
      case SystemOpenPageCommand.serialUid:
    	SystemOpenPageCommand sopCmd = (SystemOpenPageCommand) cmd;
    	Window.open(sopCmd.getUrl(), sopCmd.getName(), "");
    	break;
      default:
        Window.alert("Not implemented");
        break;
    }
  }
  
  /**
   * Handles a command error.
   * 
   * @param error the error exception
   * @param command the command that triggered the error
   * @param alternate the command to trigger on cancel
   * @param retryAttempts the number of times to retry the command before displaying an error dialog
   */
  private <E extends Command, V extends Command> void handleError(
	    Throwable error, E command, V alternate, int retryAttempts){
    clearStatus();
    if (command.getAttemptCount() <= retryAttempts) {
      command.newAttempt();
      execute(command);
    } else {
      ErrorDialog errorDialog = ErrorDialog.get(this);
      errorDialog.update(error, command, alternate);
      dialogManager.showDialog(errorDialog);
    }
  }

  /**
   * Extracts the "docid" parameter from the URL querystring and loads the
   * respective document - the document details are loaded first, followed by
   * the document contents.
   * If "docid" is blank then a new, unsaved, document is loaded.
   */
  public void loadDocument() {
    String documentId = Window.Location.getParameter("docid");
    if (documentId == null || documentId.equals("")) {
      documentId = Window.Location.getHash();
      if (documentId != null && documentId.startsWith("#")) {
    	documentId = documentId.substring(1);
      }
    }
    if (documentId == null || documentId.equals("")) {
      execute(new NewDocumentLoadCommand());
    } else {
      execute(new ExistingDocumentLoadCommand(documentId));
    }
  }
  
  /**
   * Loads the text editor with the default settings.
   * 
   * @param value the text contents
   */
  private void loadEditor(final String value) {
    showStatus("Loading advanced editor...", true);
    editor.addLoadHandler(new LoadHandler() {
        @Override
	    public void onLoad(LoadEvent event) {
          editor.setText(value);
          clearStatus();
          execute(new SystemAboutCommand());
	    }
    });
    editor.init(false, true, true, true, controlKeyHandler);
  }
  
  /**
   * Handles a command event. This is where all the components converge along with
   * the application logic.
   * 
   * @param the command event
   */
  public void onCommand(CommandEvent e) {
	execute(e.getCommand());
  }

  /**
   * Entrypoint, check for authentication.
   */
  public void onModuleLoad() {
	docService.getUser(new AsyncCallback<DocumentUser>() {
		@Override
		public void onFailure(Throwable caught) {
		  Window.alert("Authentication Failure: " + caught.getMessage());
		}
		@Override
		public void onSuccess(DocumentUser result) {
  		  if (result == null) {
  		    Window.alert("No login detected. Ensure that any requests go through the server side, " +
  		        "to enforce authentication, rather than directly to the HTML " +
  		        "content.");
  		  } else {
  		    currentUser = result;
            start();
  		  }
		}
	});
  }

  /**
   * Sets the currently open document and updates the header info and window title
   * with the respective document information.
   * 
   * @param doc the document which to use
   * @param requireNewEtag whether a new Etag is expected for the specified document
   */
  private void setDocument(DocumentServiceEntry doc, boolean requireNewEtag) {
	boolean wasStored = (currentDocument != null && currentDocument.isStored());
	boolean etagSame = false;
	if (currentDocument != null && currentDocument.getEtag() != null) {
      etagSame = currentDocument.getEtag().equals(doc.getEtag());
	}
    currentDocument = doc;
    header.setTitle(doc.getTitle());
    Window.setTitle(doc.getTitle() + " - GDBE");
    if (currentDocument.isStored()) {
      header.setInfo(currentDocument.getDocumentId(), currentDocument.getEdited(), currentDocument.getEditor());
      if (!wasStored) {
        History.newItem(doc.getDocumentId());
      } else {
	    if (etagSame) {
          if(etagSame && requireNewEtag) {
    	    new Timer() {
	            @Override
		        public void run() {
		          execute(new CurrentDocumentRefreshCommand(true, null));
		        }
            }.schedule(2000);
          }
	    }
      }
    }
  }
  
  /**
   * Shows a status message. If modal, a loading dialog is displayed, otherwise
   * the header status area is used.
   * 
   * @param message the status message to display
   * @param modal whether the status display is modal
   */
  private void showStatus(String message, boolean modal) {
    if (modal) {
      LoadingDialog loadingDialog = LoadingDialog.get(this);
      loadingDialog.setMessage(message);
      dialogManager.showDialog(loadingDialog);
    } else {
      header.setStatus(message);
    }
  }
  
  /**
   * Builds and initializes the editor module.
   */
  public void start() {
	dialogManager = new DialogManager();
    contentPane = new FlexTable();
    contentPane.setWidth("100%");
    contentPane.setHeight("100%");
    contentPane.setCellSpacing(0);
    contentPane.setCellPadding(0);
    contentPane.setBorderWidth(0);
    contentPane.insertRow(0);
    contentPane.insertCell(0, 0);
    contentPane.getFlexCellFormatter().setHeight(0, 0, "120px");
    contentPane.insertRow(1);
    contentPane.insertCell(1, 0);
    header = new HeaderPart();
    header.setAuthor(currentUser.getEmail());
    header.addCommandHandler(this);
    menu = new MenuPart();
    menu.addCommandHandler(this);
    toolbar = new ToolbarPart();
    toolbar.addCommandHandler(this);
    editor = new EditorPart();
    editor.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
	      try {
		    menu.close();
	      } catch (Exception x) { }
		}
    });
    VerticalPanel headerPanel = new VerticalPanel();
    headerPanel.setWidth("100%");
    headerPanel.add(header);
    headerPanel.add(menu);
    headerPanel.add(toolbar);
    contentPane.setWidget(0, 0, headerPanel);
    contentPane.setWidget(1, 0, editor);
    root = new AbsolutePanel();
    root.setSize("100%", "100%");
    root.add(contentPane);
    FocusPanel wrap = new FocusPanel(root);
    controlKeyHandler = new IntRunnable() {
		@Override
		public void run(int i) {
	      Event e = Event.getCurrentEvent();
	      switch(i) {
	      case 83: //CTRL+S
	    	if (e != null) e.preventDefault();
			execute(new CurrentDocumentSaveCommand());
			break;
		  case 79: //CTRL+O
	    	if (e != null) e.preventDefault();
			execute(new ExistingDocumentOpenCommand());
			break;
		  case 78: //CTRL+N
	    	if (e != null) e.preventDefault();
			execute(new NewDocumentStartCommand());
			break;
		  }
		}
    };
    wrap.addKeyDownHandler(new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
		  if (event.isControlKeyDown()) {
			controlKeyHandler.run(event.getNativeKeyCode());
		  }
		}
    });
    RootPanel.get().add(wrap);
    loadDocument();
  }
  
}