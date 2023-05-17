package prr.app.client;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
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

    if (_receiver.getAllClientsMap().get(key).getNotificationsState() == true) {
      _display.addLine(Message.clientNotificationsAlreadyEnabled());
      _display.display();
      return;
    }
    _receiver.getAllClientsMap().get(key).changeNotificationsState(true);
  }
}
