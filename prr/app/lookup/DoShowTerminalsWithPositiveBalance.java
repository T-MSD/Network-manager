package prr.app.lookup;

import java.util.ArrayList;
import java.util.List;
import prr.core.Terminal;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show terminals with positive balance.
 */
class DoShowTerminalsWithPositiveBalance extends Command<Network> {

  DoShowTerminalsWithPositiveBalance(Network receiver) {
    super(Label.SHOW_TERMINALS_WITH_POSITIVE_BALANCE, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Terminal> terminals = _receiver.getAllTerminals();
    List<String> temp = new ArrayList<>();
    for (Terminal t : terminals){
      if ((t.getPayment() - t.getDebt()) > 0){
        if (t.hasFriends()){
          _display.add(t.getType() + "|" + t.getId() + "|" + t.getClient() + "|" + t.getTerminalStatus() + "|" 
          + Math.round(t.getPayment()) + "|" + Math.round(t.getDebt()) + "|");
          temp = t.getSortedTerminalFriends();
          for (String s : temp){
            _display.add(s + ",");
          }
          _display.add("\n");
        }
        else{
          _display.addLine(t.getType() + "|" + t.getId() + "|" + t.getClient() + "|" + t.getTerminalStatus() + "|" 
          + Math.round(t.getPayment()) + "|" + Math.round(t.getDebt()));
        }
      }
    }
    _display.display();
  }
}
