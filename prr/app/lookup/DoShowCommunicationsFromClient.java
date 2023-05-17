package prr.app.lookup;

import prr.core.Client;
import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

  DoShowCommunicationsFromClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
    addStringField("key", Message.clientKey());
  }

  public Client findClient(Terminal terminal){
    Client temp = null;
    for (Client c : _receiver.getAllClients()){
      if(c.getAllClientTerminals().contains(terminal)){
          return c;
        }
      }
    return temp;
  }

  @Override
  protected final void execute() throws CommandException {
    String key = stringField("key");
    List<Communication> comms = _receiver.getAllCommunications();

    for (Communication c : comms){
      if (findClient(_receiver.getTerminal(c.getCommunicationIdSender())).getClientId().equals(key)){
        _display.addLine(c.getCommunicationType() + "|" + c.getCommunicationId() + "|" + c.getCommunicationIdSender() + "|" + 
        c.getCommunicationIdReceiver() + "|" + c.getCommunicationUnits() + "|" + c.getCommunicationPrice() + 
        "|" + c.getCommunicationStatus());
      }
    }
    _display.display();
  }
}