package prr.core;

public class Notifications {
    private String _terminalId;
    private NotificationType _type;

    public Notifications(NotificationType type, String id){
        _terminalId = id;
        _type = type;
    }

    public String getTerminalId(){
        return _terminalId;
    }

    public NotificationType getNotificationType(){
        return _type;
    }

    public String toStringNotificationType() {
        return _type.toString();
    }
}
