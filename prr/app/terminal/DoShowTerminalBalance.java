package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show balance.
 */
class DoShowTerminalBalance extends TerminalCommand {
  private Terminal _terminal;

  DoShowTerminalBalance(Network context, Terminal terminal) {
    super(Label.SHOW_BALANCE, context, terminal);
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    String key = _terminal.getId();
    long payments = _terminal.getPayment();
    long debts = _terminal.getDebt();
    _display.popup(Message.terminalPaymentsAndDebts(key, payments, debts));
  }
}
