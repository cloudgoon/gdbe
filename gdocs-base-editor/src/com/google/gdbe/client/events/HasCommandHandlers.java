package com.google.gdbe.client.events;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasCommandHandlers extends HasHandlers {
	
  /**
   * Registers a command event handler.
   * 
   * @param handler the command event handler to add
  */
  HandlerRegistration addCommandHandler(CommandHandler handler);
  
}
