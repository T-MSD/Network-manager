package prr.app.client;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
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

    if (_receiver.getAllClientsMap().get(key).getNotificationsState() == false) {
      _display.addLine(Message.clientNotificationsAlreadyDisabled());
      _display.display();
      return;
    }
    _receiver.getAllClientsMap().get(key).changeNotificationsState(false);
  }
}
