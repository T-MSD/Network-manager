package prr.app.main;

import pt.tecnico.uilib.menus.Command;
import prr.core.Communication;
import prr.core.Network;

public class DoRemoveOldComms extends Command<Network> {

  DoRemoveOldComms(Network receiver) {
    super("Remover Comunicações antigas", receiver);
    addIntegerField("number", "Número de Comunicações a remover: ");
  }

  @Override
  protected final void execute() {
    int number = integerField("number");
    int counter = 0;
    for (Communication c : _receiver.getAllCommunications()){
      if (c.getCommunicationId() < number){
        counter++;
        _display.addLine(c.getCommunicationType() + "|" + c.getCommunicationId() + "|" + c.getCommunicationIdSender() + "|" + 
        c.getCommunicationIdReceiver() + "|" + c.getCommunicationUnits() + "|" + c.getCommunicationPrice() + 
        "|" + c.getCommunicationStatus());
        c = null;
      }
    }
    _display.addLine(counter);
    _display.display();
  }
}
