package prr.app.lookup;

import java.util.*;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

  DoShowUnusedTerminals(Network receiver) {
    super(Label.SHOW_UNUSED_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Terminal> _terminals = _receiver.getAllTerminals();
    List<String> temp = new ArrayList<>();
    for (Terminal t : _terminals){
      if (t.getDebt() == 0 && t.getPayment() == 0){
        if (t.hasFriends() == false){
          _display.addLine(t.getType() + "|" + t.getId() + "|" + t.getClient() + "|" 
          + t.getTerminalStatus() + "|" + Math.round(t.getPayment()) + "|" + Math.round(t.getDebt()));
        }
        else{
          _display.add(t.getType() + "|" + t.getId() + "|" + t.getClient() + "|" 
            + t.getTerminalStatus() + "|" + Math.round(t.getPayment()) + "|" + Math.round(t.getDebt()) + "|");
          temp = t.getSortedTerminalFriends();
          for (String s : temp){
            _display.add(s + ",");
          }
          _display.add("\n");
        }
      }
    }
    _display.display();
  }
}
