package prr.app.lookup;

import java.util.List;

import prr.core.Client;
import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
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
      if (findClient(_receiver.getTerminal(c.getCommunicationIdReceiver())).getClientId().equals(key)){
        _display.addLine(c.getCommunicationType() + "|" + c.getCommunicationId() + "|" + c.getCommunicationIdSender() + "|" + 
        c.getCommunicationIdReceiver() + "|" + c.getCommunicationUnits() + "|" + c.getCommunicationPrice() + 
        "|" + c.getCommunicationStatus());
      }
    }
    _display.display();
  }
}