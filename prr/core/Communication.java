package prr.core;

import java.io.Serializable;

abstract public class Communication implements Serializable{

    private static final long serialVersionUID = 202208091753L;

    private int _id;
    private String _sender;
    private String _receiver;
    private boolean _isPaid;
    private long _cost;
    private boolean _isOnGoing;
    private String _type;
    protected int _duration;
    protected static int _communicationNumber = 1;

    public Communication(String sender, String receiver, String type){
      _sender = sender;
      _receiver = receiver;
      _type = type;
      _id = _communicationNumber;
      _isPaid = false;
      _isOnGoing = false;
    }

    public int getCommunicationId(){
      return _id;
    }

    public String getCommunicationType(){
      return _type;
    }

    public String getCommunicationIdSender(){
      return _sender;
    }

    public String getCommunicationIdReceiver(){
      return _receiver;
    }

    public int getCommunicationUnits(){
      return getUnits();
    }

    public String getCommunicationStatus(){
      if (_isOnGoing){
        return "ONGOING";
      }
      return "FINISHED";
    }

    public long getCommunicationPrice(){
      return computeCost();
    }

    public boolean getIsPaid() {
      return _isPaid;
    }

    public void changeIsPaid(boolean isPaid) {
      _isPaid = isPaid;
    }

    public static void increaseCommNumber() {
      _communicationNumber++;
    }

    public void startComm() {
      _isOnGoing = true;
    }

    public void endComm() {
      _isOnGoing = false;
    }

    public boolean getOnGoing() {
      return _isOnGoing;
    }

    public void setCost(long number) {
      _cost = number;
    }

    public void changeDuration(int number){
      _duration = number;
    }

    public String getCommReceiver(){
      return _receiver;
    }

    public long getCommunicationCost(){
      return _cost;
    }

    abstract public int getUnits();
    abstract public long computeCost();
  }
