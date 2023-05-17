package prr.app.terminals;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.*;

import prr.core.Network;
import prr.core.Terminal;


/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Terminal> terminals = _receiver.getAllTerminals();
    List<String> temp = new ArrayList<>();
    int size;
    for (Terminal t : terminals){
      if (t.hasFriends()){
        _display.addLine(t.getType() + "|" + t.getId() + "|" + t.getClient() + "|" + t.getTerminalStatus() + "|" 
        + Math.round(t.getPayment()) + "|" + Math.round(t.getDebt()) + "|");
        temp = t.getSortedTerminalFriends();
        size = temp.size();
        for (String s : temp){
          if (size == 1){
            _display.add(s);
          }
          else{
            _display.add(s + ",");
            size--;
          }
        }
      }
      else{
        _display.addLine(t.getType() + "|" + t.getId() + "|" + t.getClient() + "|" + t.getTerminalStatus() + "|" 
        + Math.round(t.getPayment()) + "|" + Math.round(t.getDebt()));
      }
    }
    _display.display();
  }
}
