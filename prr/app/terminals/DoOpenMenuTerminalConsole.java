package prr.app.terminals;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.*;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
  }

  @Override
  protected final void execute() throws CommandException {
    List<Terminal> terminals = _receiver.getAllTerminals();
    String id = stringField("id");

    for (Terminal t : terminals) {
      if (t.getId().equals(id)) {
        (new prr.app.terminal.Menu(_receiver , _receiver.getTerminal(id))).open();
        return;
      }
    }
    throw new UnknownTerminalKeyException(id);
  }
  }

