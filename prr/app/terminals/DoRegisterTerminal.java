package prr.app.terminals;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalBasic;
import prr.core.TerminalFancy;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
    addStringField("client", Message.clientKey());
  }

  public void checkId(String id) throws InvalidTerminalKeyException{

    if (Integer.compare(id.length(), 6) != 0) {
      throw new InvalidTerminalKeyException(id);
    }

    try {
      Integer.parseInt(id);
    }

    catch (NumberFormatException e) {
      throw new InvalidTerminalKeyException(id);
    }
  }

  public void checkDuplicate(String id) throws DuplicateTerminalKeyException {

    if (_receiver.getAllTerminalsMap().containsKey(id)) {
      throw new DuplicateTerminalKeyException(id);
    }
  }

  public void checkClient(String key) throws UnknownClientKeyException{

    
    if (_receiver.getAllClientsMap().containsKey(key)) {
      return;
    }
    
    throw new UnknownClientKeyException(key);
  }

  public void checkNumberOfTerminals(String key) throws UnknownTerminalKeyException{
    int clients = _receiver.getAllClients().size()*2;
    int terminals = _receiver.getAllTerminals().size() + 1;
    if (clients < terminals){
      throw new UnknownTerminalKeyException(key);
    }
  }

  @Override
  protected final void execute() throws CommandException {
    String client = stringField("client");
    String id = stringField("id");
    String type = stringField("type");
    Terminal terminal;
    
    checkId(id);
    checkDuplicate(id);
    checkClient(client);
    checkNumberOfTerminals(id);

    if (type.equals("FANCY")){
      terminal = new TerminalFancy(id, type, client);
    }
    else{
      terminal = new TerminalBasic(id, type, client);
    }

    _receiver.addTerminalNetwork(id, terminal);
    _receiver.getClient(client).addTerminal(terminal);
  }
}
