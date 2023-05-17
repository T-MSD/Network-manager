package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalStatus;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {
  private Terminal _terminal;

  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (!_terminal.getTerminalStatus().equals(TerminalStatus.IDLE)){
      _terminal.saveInitialStatus();
      _terminal.turnOn();
      /**/_terminal.checkNotificationType(TerminalStatus.IDLE);
      return;
    }
    _display.popup(Message.alreadyOn());
  }
}
