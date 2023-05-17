package prr.core;

import java.io.Serializable;
import java.util.*;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  private String _client;
  private String _id;
  private String _type;
  private long _payment;
  private long _debt;
  private boolean _sender;
  private TerminalStatus _status;
  private List<String> _friends;
  private List<Communication> _terminalCommunications;
  private TerminalStatus _initialStatus;
  private List<Client> _toNotify;

  public Terminal(String id, String type, String client){
    _client = client;
    _id = id;
    _type = type;
    _payment = 0;
    _debt = 0;
    _friends = new ArrayList<>();
    _terminalCommunications = new ArrayList<>();
    _toNotify = new ArrayList<>();
    _status = TerminalStatus.IDLE;
  }

  public boolean hasFriends(){
    if (_friends.size() > 0){
      return true;
    }
    return false;
  }

  public List<Client> getClientsToNotify(){
    return _toNotify;
  }

  public void addClientToNotify(Client client) {
    _toNotify.add(client);
  }

  public List<String> getSortedTerminalFriends(){
    List<String> friends = _friends;
    Collections.sort(friends, new Comparator<String>() {
      public int compare(String I1, String I2){
        return I1.compareTo(I2);
      }
    });
    return friends;
  }

  public List<String> getTerminalFriends() {
    return _friends;
  }

  public TerminalStatus getTerminalStatus(){
    return _status;
  }
  
  public String getClient(){
    return _client;
  }

  public String getId(){
    return _id;
  }

  public String getType(){
    return _type;
  }

  public long getDebt(){
    return _debt;
  }

  public void changeDebt(double cost) {
    _debt -= cost;
  }
  
  public long getPayment(){
    return _payment;
  }

  public void changePayment(double cost) {
    _payment += cost;
  }

  public List<Communication> getTerminalCommunications(){
    return _terminalCommunications;
  }

  public Communication getCommunication(int key) {
    Communication temp = null;
    for (Communication c : _terminalCommunications) {
      if (c.getCommunicationId() == key) {
        temp = c;
      }
    }
    return temp;
  }



  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    if (_status == TerminalStatus.BUSY && _sender) {
      return true;
    }
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    if (_status.equals(TerminalStatus.OFF) | _status.equals(TerminalStatus.BUSY)) {
      return false;
    }
    return true;
  }

  public void saveInitialStatus() {
    _initialStatus = _status;
  }

  public TerminalStatus getInitialStatus() {
    return _initialStatus;
  }

  public void setOnSilent() {
    _status = TerminalStatus.SILENCE;
  }

  public void turnOff() {
    _status = TerminalStatus.OFF;
  }

  public void turnOn() {
    _status = TerminalStatus.IDLE;
  }

  public void setTerminalStatus(TerminalStatus status){
    _status = status;
  }

  public void checkNotificationType(TerminalStatus status) {
    Notifications n = null;
    NotificationType res = null;

    if (getInitialStatus().toString().equals("OFF")  && status.toString().equals("SILENCE")){
      res = NotificationType.O2S;
    }
    else if (getInitialStatus().toString().equals("OFF") && status.toString().equals("IDLE")){
      res = NotificationType.O2I;
    }
    else if (getInitialStatus().toString().equals("BUSY") && status.toString().equals("IDLE")){
      res = NotificationType.B2I;
    }
    else if (getInitialStatus().toString().equals("SILENCE") && status.toString().equals("IDLE")){
      res = NotificationType.S2I;
    }

    if (res != null) {
      n = new Notifications(res, getId());
      for (Client c : _toNotify){
        if (c.getNotificationsState() && !c.getAllClientNotifications().contains(n)){
          c.addClientNotification(n);
        }
      }
      _toNotify.clear();
    }
  }

  public void addTerminalDebt(long number){
    _debt += (int)number;
  }

  public void changeTerminalSender(boolean sender){
    _sender = sender;
  }

  public void addFriends(String id){
    _friends.add(id);
  }

  public void removeFriends(String id) {
    _friends.remove(id);
  }
}
