package prr.core;

import java.io.Serializable;
import java.util.*;

abstract public class Client implements Serializable{

  private static final long serialVersionUID = 202208091753L;
  
  private String _clientId;
  private String _name;
  private String _taxNumber;
  private boolean _receiveNotifications;
  private ClientLevel _level;
  private List<Terminal> _clientTerminals;
  private List<Notifications> _clientNotifications;
  private int _balance;


  public Client(String clientId, String name, int taxNumber){
    _clientTerminals = new ArrayList<>();
    _clientNotifications = new ArrayList<>();
    _clientId = clientId;
    _name = name;
    _taxNumber = Integer.toString(taxNumber);
    _receiveNotifications = true;
    _level = ClientLevel.NORMAL;
    _balance = 0;
  }

  public List<Notifications> getAllClientNotifications(){
    return _clientNotifications;
  }

  public void addClientNotification(Notifications notification) {
    _clientNotifications.add(notification);
  }

  public String getClientId(){
    return _clientId;
  }

  public String getName(){
    return _name;
  }

  public String getTaxNumber(){
    return _taxNumber;
  }

  public String getNotifications(){
    if (_receiveNotifications == true){
      return "YES";
    }
    return "NO";
  }

  public boolean getNotificationsState() {
    return _receiveNotifications;
  }

  public void changeNotificationsState(boolean state) {
    _receiveNotifications = state;
  }

  public ClientLevel getClientLevel(){
    return _level;
  }

  public int getClientTerminalsSize(){
    return _clientTerminals.size();
  }

  public void addTerminal(Terminal terminal){
    _clientTerminals.add(terminal);
  }

  public int getClientPayments(){
    int count = 0;
    for (Terminal t : _clientTerminals){
      count += t.getPayment();
    }
    return count;
  }

  public int getClientDebts(){
    int count = 0;
    for (Terminal t : _clientTerminals){
      count += t.getDebt();
    }
    return count;
  }
  public List<Terminal> getAllClientTerminals(){
    return _clientTerminals;
  }

  public void clearClientNotifications(){
    _clientNotifications = null;
  }

  public void setClientLevel(ClientLevel level){
    _level = level;
  }

  public void demoteClient(){
    setClientBalance();
    if ((_level.equals(ClientLevel.GOLD) | _level.equals(ClientLevel.PLATINUM)) && _balance < 0){
      _level = ClientLevel.NORMAL;
    }
  }

  public void promoteClient(){
    setClientBalance();
    if (_level.equals(ClientLevel.NORMAL) && _balance > 500){
      _level = ClientLevel.GOLD;
    }
  }

  public int getClientBalance(){
    return _balance;
  }

  public void setClientBalance(){
    for (Terminal t : _clientTerminals){
      _balance += (t.getPayment() - t.getDebt());
    }
  }

  public void changeBalance(double cost){
    _balance -= (int)cost;
  }  
}