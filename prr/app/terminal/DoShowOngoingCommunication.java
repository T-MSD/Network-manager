package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

import java.util.*;

/**
 * Command for showing the ongoing communication.
 */
class DoShowOngoingCommunication extends TerminalCommand {

  DoShowOngoingCommunication(Network context, Terminal terminal) {
    super(Label.SHOW_ONGOING_COMMUNICATION, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    List<Communication> comms = _receiver.getTerminalCommunications();

    for (Communication c : comms) {
      if (c.getOnGoing()) {
        _display.popup(c.getCommunicationType() + "|" + c.getCommunicationId() + "|" + c.getCommunicationIdSender() + "|" + 
        c.getCommunicationIdReceiver() + "|" + c.getCommunicationUnits() + "|" + c.getCommunicationPrice() + 
        "|" + c.getCommunicationStatus());
        return;
      }
    }
   _display.popup(Message.noOngoingCommunication());
  }
}  
