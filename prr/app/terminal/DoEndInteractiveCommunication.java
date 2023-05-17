package prr.app.terminal;

import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {
  private Terminal _terminal;
  private Network _network;

  DoEndInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
    _terminal = terminal;
    _network = context;
    addIntegerField("time", Message.duration());
  }
  
  @Override
  protected final void execute() throws CommandException {
    long cost;
    int time = integerField("time");
    for (Communication c : _terminal.getTerminalCommunications()){
      if (c.getOnGoing()){
        _network.getAllCommunications().remove(c);
        Terminal terminalReceiver = _network.getTerminal(c.getCommReceiver());
        c.endComm();
        c.changeDuration(time);
        if (_terminal.getTerminalFriends().contains(terminalReceiver.getId())) {
          cost = c.computeCost() / 2;
        }
        else {
          cost = c.computeCost();
        }
        c.setCost(cost);
        _display.popup(Message.communicationCost(cost));
        _terminal.addTerminalDebt(cost);
        _terminal.setTerminalStatus(_terminal.getInitialStatus());
        _terminal.changeTerminalSender(false);
        terminalReceiver.setTerminalStatus(terminalReceiver.getInitialStatus());
        terminalReceiver.checkNotificationType(terminalReceiver.getInitialStatus());
        _network.getAllCommunications().add(c);
        break;
        }
      }
    }
}
