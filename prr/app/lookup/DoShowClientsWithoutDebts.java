package prr.app.lookup;

import java.util.List;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

  DoShowClientsWithoutDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Client> clients = _receiver.getAllClients();
    for (Client c : clients){
      if (c.getClientDebts() == 0){
        _display.addLine("CLIENT" + "|" + c.getClientId() + "|" + c.getName() + "|" + 
        c.getTaxNumber() + "|" + c.getClientLevel() + "|" + c.getNotifications() + 
        "|" + c.getClientTerminalsSize() + "|" + Math.round(c.getClientPayments()) + "|" + Math.round(c.getClientDebts()));
      }
    }
    _display.display();
  }
}
