package prr.app.main;

import prr.app.exception.FileOpenFailedException;
import prr.core.NetworkManager;
import prr.core.exception.UnavailableFileException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
      addStringField("file", Message.openFile());
  }
  
  @Override
  protected final void execute() throws CommandException, FileOpenFailedException {
    String file = stringField("file");
    try {
      _receiver.load(file);
      _receiver.setFileName(file);
      _receiver.changeOpened();
    } catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe);
    }
  }
}
