package prr.app.client;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField("key", Message.key());
  }

  public void checkClientKey(String key) throws UnknownClientKeyException {
    if (!(_receiver.getAllClientsMap().containsKey(key))) {
      throw new UnknownClientKeyException(key);
    }
    return;
  }
  
  @Override
  protected final void execute() throws CommandException {
    String key = stringField("key");

    checkClientKey(key);

    int payments = _receiver.getAllClientsMap().get(key).getClientPayments();
    int debts = _receiver.getAllClientsMap().get(key).getClientDebts();
     
    _display.addLine(Message.clientPaymentsAndDebts(key, payments, debts));
    _display.display();
  }
}
