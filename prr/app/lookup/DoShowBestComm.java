package prr.app.lookup;

import prr.core.Communication;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

public class DoShowBestComm extends Command<Network>{
  
  DoShowBestComm(Network receiver) {
    super("Melhor Comunicação", receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    Communication max = null;
    if (_receiver.getAllCommunications().size() == 0){
      _display.popup("Não existe nenhuma Comunicação");
      return;
    }
    for (Communication c : _receiver.getAllCommunications()){
      if (max == null){
        max = c;
      }
      else if (c.getIsPaid() && (c.getCommunicationUnits() > max.getCommunicationUnits())){
        max = c;
      }
    }
    _display.popup(max.getCommunicationType() + "|" + max.getCommunicationId() + "|" + max.getCommunicationIdSender() + "|" + 
    max.getCommunicationIdReceiver() + "|" + max.getCommunicationUnits() + "|" + max.getCommunicationPrice() + 
    "|" + max.getCommunicationStatus());
  }
}
