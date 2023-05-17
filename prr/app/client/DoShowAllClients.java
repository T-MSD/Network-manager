package prr.app.client;

import java.util.*;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    List<Client> clients = _receiver.getAllClients();
    for (Client c : clients){
      _display.addLine("CLIENT" + "|" + c.getClientId() + "|" + c.getName() + "|" + 
      c.getTaxNumber() + "|" + c.getClientLevel() + "|" + c.getNotifications() + 
      "|" + c.getClientTerminalsSize() + "|" + Math.round(c.getClientPayments()) + "|" + Math.round(c.getClientDebts()));
    }
    _display.display();
  }
}
