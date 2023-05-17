package prr.core;

public class VideoCommunication extends InteractiveCommunication {
  
  private ClientLevel _clientLevel;

public VideoCommunication(ClientLevel clientLevel, String sender, String receiver){
  super(sender, receiver, "VIDEO");
  _clientLevel = clientLevel;
}

  public long computeCost(){
    long price;
    switch (_clientLevel) {
      case GOLD -> price = doPrice(ClientLevel.GOLD);
      case PLATINUM -> price = doPrice(ClientLevel.PLATINUM);
      default -> price = doPrice(ClientLevel.NORMAL);
    }
    return price;
  }

  public long doPrice(ClientLevel level){
    if (level.equals(ClientLevel.GOLD)){
      return getUnits()*20;
    }
    else if (level.equals(ClientLevel.PLATINUM)){
      return getUnits()*10;
    }
    return getUnits()*30;
  }
}
