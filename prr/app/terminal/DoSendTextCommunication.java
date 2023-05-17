package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Client;
import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalStatus;
import prr.core.TextCommunication;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {
  private Network _network;
  private Terminal _terminal;

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    _network = context;
    _terminal = terminal;
    addStringField("id", Message.terminalKey());
    addStringField("text", Message.textMessage());
  }
  
  public Client findClient(Terminal terminal){
    Client temp = null;
    for (Client c : _network.getAllClients()){
      if(c.getAllClientTerminals().contains(terminal)){
          return c;
        }
      }
    return temp;
  }

  public int countCharacters(String text){
    return text.length();
  }

  public void checkTerminalId(String id) throws UnknownTerminalKeyException{
    Terminal terminal = _network.getTerminal(id);
    if (_network.getAllTerminals().contains(terminal)) {
      return;
    }
    throw new UnknownTerminalKeyException(id);
  }

  @Override
  protected final void execute() throws CommandException {
    String text = stringField("text");
    String id = stringField("id");
    long cost;
    checkTerminalId(id);
    
    Client c = findClient(_terminal);
    Terminal terminal = _network.getTerminal(id);

    if (terminal.getTerminalStatus().equals(TerminalStatus.OFF)){
      _display.popup(Message.destinationIsOff(id));
      terminal.addClientToNotify(c);
      return;
    }
    
    TextCommunication textComm = new TextCommunication(c.getClientLevel(), countCharacters(text), _terminal.getId(), id);
    cost = textComm.computeCost();
    textComm.changeCharacters(countCharacters(text));
    textComm.changeCost(cost);
    _terminal.addTerminalDebt(cost);
    _terminal.getTerminalCommunications().add(textComm);
    _network.getAllCommunications().add(textComm);
    Communication.increaseCommNumber();
  }
} 
