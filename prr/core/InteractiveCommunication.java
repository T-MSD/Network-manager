package prr.core;

abstract public class InteractiveCommunication extends Communication {

  public InteractiveCommunication(String sender, String receiver, String type){
    super(sender, receiver, type);
  }

    abstract public long doPrice(ClientLevel level);
    
    public int getUnits(){
      return _duration;
    }
}
