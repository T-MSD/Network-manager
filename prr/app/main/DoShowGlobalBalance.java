package prr.app.main;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<Network> {

  DoShowGlobalBalance(Network receiver) {
    super(Label.SHOW_GLOBAL_BALANCE, receiver);
  }

  public int doGlobalPayments() {

    int _totalPayments = 0;

    for (Client c : _receiver.getAllClients()) {
      _totalPayments += c.getClientPayments();
    }

    return _totalPayments;
  
  }

  public int doGlobalDebts() {

    int _totalDebts = 0;

    for (Client c : _receiver.getAllClients()) {
      _totalDebts += c.getClientDebts();
    }

    return _totalDebts;
  }
  
  @Override
  protected final void execute() throws CommandException {


    _display.addLine(Message.globalPaymentsAndDebts(Math.round(doGlobalPayments()), Math.round(doGlobalDebts())));
    _display.display();
  }
}
