package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand {

  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("key", Message.terminalKey());
  }

  public void checkTerminalKey() throws UnknownTerminalKeyException {
    String key = stringField("key");
    if (!(_network.getAllTerminalsMap().containsKey(key))) {
      throw new UnknownTerminalKeyException(key);
    }
  }

  @Override
  protected final void execute() throws CommandException {

    String key = stringField("key");

    checkTerminalKey();

    if (_network.getAllTerminalsMap().get(_receiver.getId()).getTerminalFriends().contains(key) | _receiver.getId().equals(key)) {
      return;
    }
    else {
      _network.getAllTerminalsMap().get(_receiver.getId()).addFriends(key);
    }
    
  }
}
