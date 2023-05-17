package prr.app.terminal;

import prr.core.Client;
import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {
  private Network _network;

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    addIntegerField("key", Message.commKey());
    _network = context;
  }
  
  @Override
  protected final void execute() throws CommandException, NullPointerException{
    int key = integerField("key");
    try{
      Communication c = _receiver.getCommunication(key);
      Client client = _network.getClient(_receiver.getClient());
      double cost = c.getCommunicationPrice();
      if ((c.getIsPaid()) | (c.getCommunicationStatus() == "ONGOING")) {
        _display.popup(Message.invalidCommunication());
      }
      else {
        c.changeIsPaid(true);
        _receiver.changeDebt(cost);
        _receiver.changePayment(cost);
        client.changeBalance(cost);
        client.promoteClient();
        client.demoteClient();
      }
    }catch (NullPointerException npe){
      _display.popup(Message.invalidCommunication());
    }
  }
}

