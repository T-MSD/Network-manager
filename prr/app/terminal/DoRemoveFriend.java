package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("key", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException {

    String key = stringField("key");
    
    if(_network.getAllTerminalsMap().get(_receiver.getId()).getTerminalFriends().contains(key)) {
      _network.getAllTerminalsMap().get(_receiver.getId()).removeFriends(key);
      return;
    }


  }
}
