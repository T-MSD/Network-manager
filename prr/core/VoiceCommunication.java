package prr.core;

public class VoiceCommunication extends InteractiveCommunication {
    
  private ClientLevel _clientLevel;

  public VoiceCommunication(ClientLevel clientLevel, String sender, String receiver){
    super(sender, receiver, "VOICE");
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
      return getUnits()*10;
    }
    else if (level.equals(ClientLevel.PLATINUM)){
      return getUnits()*10;
    }
    return getUnits()*20;
  }
}
