package com.google.gdbe.explorer.client;

import com.google.gdbe.client.commands.Command;
import com.google.gdbe.client.commands.ExistingDocumentOpenCommand;
import com.google.gdbe.client.commands.FileDialogListDocumentsCommand;
import com.google.gdbe.client.commands.FileDialogOpenDocumentCommand;
import com.google.gdbe.client.commands.FileDialogStarDocumentCommand;
import com.google.gdbe.client.commands.FileDialogUnstarDocumentCommand;
import com.google.gdbe.client.commands.NewDocumentStartCommand;
import com.google.gdbe.client.commands.SystemSignOutCommand;
import com.google.gdbe.client.dialogs.DialogManager;
import com.google.gdbe.client.dialogs.ErrorDialog;
import com.google.gdbe.client.dialogs.FileListDialog;
import com.google.gdbe.client.dialogs.LoadingDialog;
import com.google.gdbe.client.events.CommandEvent;
import com.google.gdbe.client.events.CommandHandler;
import com.google.gdbe.client.gdocs.DocumentService;
import com.google.gdbe.client.gdocs.DocumentServiceAsync;
import com.google.gdbe.client.gdocs.DocumentServiceEntry;
import com.google.gdbe.client.gdocs.DocumentUser;
import com.google.gdbe.client.resources.icons.Icons;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.Date;

/**
 * The GDBE document explorer module.
 */
public class GdbeExplorer implements EntryPoint, CommandHandler {

  private final DocumentServiceAsync docService = GWT.create(DocumentService.class);
  private HorizontalPanel contentPane;
  private DialogManager dialogManager;
  @SuppressWarnings("unused")
  private DocumentUser currentUser;
  private DocumentServiceEntry[] allDocuments;

  /**
   * Clears and hides any visible status messages.
   */
  private void clearStatus() {
	LoadingDialog.get(this).hide();
    Window.setStatus("");
  }
  
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
    	  FileListDialog fileListDialog = FileListDialog.get(GdbeExplorer.this);
          fileListDialog.setEntries(allDocuments);
          fileListDialog.showEntries();
    	} else {
          docService.getDocuments(false, new AsyncCallback<DocumentServiceEntry[]>() {
            public void onFailure(Throwable caught) {
              handleError(caught, cmd, null, 1);
            }
            public void onSuccess(DocumentServiceEntry[] result) {
              allDocuments = result;
        	  FileListDialog fileListDialog = FileListDialog.get(GdbeExplorer.this);
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
      Window.setStatus(message);
    }
  }
  
  /**
   * Builds and initializes the explorer module.
   */
  public void start() {
	dialogManager = new DialogManager();
    contentPane = new HorizontalPanel();
    contentPane.setWidth("100%");
    contentPane.setHeight("100%");
    contentPane.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
    contentPane.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    Image logo = Icons.editorIcons.LogoLarge().createImage();
    logo.addClickHandler(new ClickHandler(){
      public void onClick(ClickEvent event) {
    	execute(new ExistingDocumentOpenCommand());
      }
    });
    contentPane.add(logo);
    RootPanel.get().add(contentPane);
	execute(new ExistingDocumentOpenCommand());
  }
  
}