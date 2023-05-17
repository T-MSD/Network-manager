package prr.app.lookup;

import java.util.List;

import prr.core.Communication;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for showing all communications.
 */
class DoShowAllCommunications extends Command<Network> {

  DoShowAllCommunications(Network receiver) {
    super(Label.SHOW_ALL_COMMUNICATIONS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Communication> comms = _receiver.getAllCommunications();
    for (Communication c : comms){
      _display.addLine(c.getCommunicationType() + "|" + c.getCommunicationId() + "|" + c.getCommunicationIdSender() + "|" + 
    c.getCommunicationIdReceiver() + "|" + c.getCommunicationUnits() + "|" + c.getCommunicationPrice() + 
    "|" + c.getCommunicationStatus());
    }
    _display.display();
  }
}
