package prr.app.client;

import java.util.*;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Client;
import prr.core.Network;
import prr.core.Notifications;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField("client", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    String client = stringField("client");
    Client c = _receiver.getClient(client);
    List<Notifications> temp = new ArrayList<>();
    int size;

    try {
      _display.addLine("CLIENT" + "|" + c.getClientId() + "|" + c.getName() + "|" + 
      c.getTaxNumber() + "|" + c.getClientLevel() + "|" + c.getNotifications() + 
      "|" + c.getClientTerminalsSize() + "|" + Math.round(c.getClientPayments()) + "|" + Math.round(c.getClientDebts()));
      temp = c.getAllClientNotifications();
      size = temp.size();
      if (size > 0){
        for (Notifications n : temp){
          _display.addLine(n.getNotificationType() + "|" + n.getTerminalId());
        }
        c.getAllClientNotifications().clear();
      }
      _display.display();
      
    }
    catch (NullPointerException npe) {
      throw new UnknownClientKeyException(client);
    }
  }
}
