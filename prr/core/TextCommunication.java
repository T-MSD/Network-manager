package prr.core;

public class TextCommunication extends Communication {

    private int _characters;
    private ClientLevel _clientLevel;
    private long _cost;

    public TextCommunication(ClientLevel clientLevel, int characters, String sender, String receiver) {
      super(sender, receiver, "TEXT");
        _clientLevel = clientLevel;
        _characters = characters;
    }

    public int getUnits(){
      return _characters;
    }

    public long doPriceNormal() {
        if (_characters < 50) {
            _cost = 10;
        }
        else if (_characters >= 50 && _characters < 100) {
            _cost = 16;
        }
        else if ( _characters >= 100) {
            _cost = 2 * _characters;
        }
        return _cost;
        
    }

    public long doPriceGold() {
        if (_characters < 50) {
            _cost = 10;
        }
        else if (_characters >= 50 && _characters < 100) {
            _cost = 10;
        }
        else if ( _characters >= 100) {
            _cost = 2 * _characters;
        }
        return _cost;
    }

    public long doPricePlatinum() {
        if (_characters < 50) {
            _cost = 0;
        }
        else if (_characters >= 50 && _characters < 100) {
            _cost = 4;
        }
        else if ( _characters >= 100) {
            _cost = 4;
        }
        return _cost;
    }

    public long computeCost() {
        long price;
        switch (_clientLevel) {
            case GOLD -> price = doPriceGold();
            case PLATINUM -> price = doPricePlatinum();
            default -> price = doPriceNormal();
        }
        return price;
    }

    public void changeCharacters(int number){
      _characters = number;
    }

    public void changeCost(long number){
      _cost = number;
    }
}