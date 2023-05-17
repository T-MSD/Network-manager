package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Client;
import prr.core.Communication;
import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalStatus;
import prr.core.VideoCommunication;
import prr.core.VoiceCommunication;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {
  private Network _network;
  private Terminal _terminal;
  
  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    _network = context;
    _terminal = terminal;
    addStringField("id", Message.terminalKey());
    addOptionField("comm", Message.commType(), "VOICE", "VIDEO");
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
  
  public void createComm(String type, String id, Terminal terminal){
    Client c = findClient(_terminal);
    if (type.equals("VIDEO")){
      VideoCommunication videoComm = new VideoCommunication(c.getClientLevel(), _terminal.getId(), id);
      _terminal.getTerminalCommunications().add(videoComm);
      _network.getAllCommunications().add(videoComm);
      videoComm.startComm();
    }
    else if(type.equals("VOICE")){
      VoiceCommunication voiceComm = new VoiceCommunication(c.getClientLevel(), _terminal.getId(), id);
      _terminal.getTerminalCommunications().add(voiceComm);
      _network.getAllCommunications().add(voiceComm);
      voiceComm.startComm();
    }

    
    _terminal.setTerminalStatus(TerminalStatus.BUSY);
    _terminal.changeTerminalSender(true);
    terminal.setTerminalStatus(TerminalStatus.BUSY);
    

    Communication.increaseCommNumber();
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
    String comm = stringField("comm");
    String id = stringField("id");

    checkTerminalId(id);

    Terminal terminal = _network.getTerminal(id);
    Client c = findClient(_terminal);

    _terminal.saveInitialStatus();
    terminal.saveInitialStatus();

    if (terminal.getTerminalStatus().equals(TerminalStatus.OFF)){
      _display.popup(Message.destinationIsOff(id));
      terminal.addClientToNotify(c);
      return;
    }
    else if (terminal.getTerminalStatus().equals(TerminalStatus.BUSY)){
      _display.popup(Message.destinationIsBusy(id));
      terminal.addClientToNotify(c);
      return;
    }
    else if (terminal.getTerminalStatus().equals(TerminalStatus.SILENCE)){
      _display.popup(Message.destinationIsSilent(id));
      terminal.addClientToNotify(c);
      return;
    }
    else if (_terminal.getType().equals("BASIC") && comm.equals("VIDEO")){
      _display.popup(Message.unsupportedAtOrigin(id, comm));
      return;
    }
    else if (terminal.getType().equals("BASIC") && comm.equals("VIDEO")){
      _display.popup(Message.unsupportedAtDestination(id, comm));
      return;
    } 
    else if (_terminal.getId().equals(id)){
      _display.popup(Message.destinationIsBusy(id));
      return;
    }
    createComm(comm, id, terminal);
    
  }
}
