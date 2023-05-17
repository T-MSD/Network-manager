package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalStatus;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {
  private Terminal _terminal;

  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (!_terminal.getTerminalStatus().equals(TerminalStatus.OFF)){
      _terminal.turnOff();
      return;
    }
    _display.popup(Message.alreadyOff());
  }
}
