package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalStatus;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Silence the terminal.
 */
class DoSilenceTerminal extends TerminalCommand {
  private Terminal _terminal;

  DoSilenceTerminal(Network context, Terminal terminal) {
    super(Label.MUTE_TERMINAL, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if (!_terminal.getTerminalStatus().equals(TerminalStatus.SILENCE)){
      _terminal.saveInitialStatus();
      _terminal.setOnSilent();
      /**/_terminal.checkNotificationType(TerminalStatus.SILENCE);
      return;
    }
    _display.popup(Message.alreadySilent());
  }
}
