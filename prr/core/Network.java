package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.*;

import prr.app.exception.DuplicateClientKeyException;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.UnrecognizedEntryException;

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;

  private Map<String, Terminal> _terminals;
  private Map<String, Client> _clients;
  private List<Communication> _communications;
  
  public Network(){
    _terminals = new TreeMap<>();
    _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    _communications = new ArrayList<>();
  }
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO error while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }

  public List<Communication> getAllCommunications(){
    return _communications;
  }
  /**
   * returns terminal with the given id.
   **/

  public Terminal getTerminal(String id) {
    Terminal temp = null;
    if (_terminals.containsKey(id)) {
      temp = _terminals.get(id);
    }
    return temp;
  }

  /**
   * returns list of existing terminals.
   **/

  public List<Terminal> getAllTerminals(){
    List<Terminal> terminalsList = new ArrayList<>(_terminals.values());
    return terminalsList;
  }

  public Map<String, Terminal> getAllTerminalsMap() {
    return _terminals;
  }

  /**
   * returns client with the given id.
   **/

  public Client getClient(String key){
    Client temp = null;
    if (_clients.containsKey(key)){
      temp = _clients.get(key);
    }
    return temp;
  }

  public void addTerminalNetwork(String id, Terminal terminal) {
    _terminals.put(id, terminal);
  }

  /**
   *  returns list of existing clients.
   **/

  public List<Client> getAllClients(){
    List<Client> clientsList = new ArrayList<>(_clients.values());
    return clientsList;
  }

  public Map<String, Client> getAllClientsMap() {
    return _clients;
  }

  public void addClientNetwork(String key, Client client){
    _clients.put(key, client);
  }

  /**
   * When given the correct inputs registers a client.
   **/

  public Client registerClient(String key, String name, int taxId) throws DuplicateClientKeyException {
    Client client = new ClientNormal(key, name, taxId);
    _clients.put(client.getClientId(), client);
    return client;
  }

  /**
   * When given the correct inputs registers a terminal, can either be basic or fancy.
   **/

  public Terminal registerTerminal(String client, String id, String type) throws UnknownClientKeyException, DuplicateTerminalKeyException{
    Terminal terminal;
    if (type.equals("FANCY")){
      terminal = new TerminalFancy(id, type, client);
      _terminals.put(terminal.getId(), terminal);
      this.getClient(client).addTerminal(terminal);
    }
    else{
      terminal = new TerminalBasic(id, type, client);
      _terminals.put(terminal.getId(), terminal);
      this.getClient(client).addTerminal(terminal);
    }
    return terminal;
  }
	public void addFriend(String terminal, String friend) throws InvalidTerminalKeyException{
    _terminals.get(terminal).addFriends(friend);
	}
}

