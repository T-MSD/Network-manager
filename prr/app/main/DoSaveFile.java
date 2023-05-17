package prr.app.main;

import java.io.IOException;

import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  private String _res;

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }

  public void createFile(){
    _receiver.setFileName(_res);
  }
  
  @Override
  protected final void execute(){
    if (!_receiver.getOpened()){
      _res = Form.requestString(Message.newSaveAs());
      createFile();
      _receiver.changeOpened();
      try {
        _receiver.saveAs(_res);
      } catch (MissingFileAssociationException | IOException e) {
        System.err.println(e.getMessage());
      }
    }
    else{
      try {
        _receiver.save();
      } catch (MissingFileAssociationException | IOException e) {
        System.err.println(e.getMessage());
      }
    }
  }
}
