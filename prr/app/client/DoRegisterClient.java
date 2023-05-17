package prr.app.client;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;

import prr.app.exception.DuplicateClientKeyException;
import prr.core.Client;
import prr.core.ClientNormal;
import prr.core.Network;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("taxId", Message.taxId());
  }

  public void checkDuplicateClient(String key) throws DuplicateClientKeyException {
    List<Client> clients = _receiver.getAllClients();

    for (Client c : clients) {
      if (c.getClientId().equals(key)) {
        throw new DuplicateClientKeyException(key);
      }
    }
  }

  @Override
  protected final void execute() throws CommandException, DuplicateClientKeyException{
    String key = stringField("key");
    String name = stringField("name");
    int taxId = integerField("taxId");

    checkDuplicateClient(key);

    Client client = new ClientNormal(key, name, taxId);
    _receiver.addClientNetwork(key, client);
  }
}
