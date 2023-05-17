package prr.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import prr.core.exception.ImportFileException;
import prr.core.exception.MissingFileAssociationException;
import prr.core.exception.UnavailableFileException;
import prr.core.exception.UnrecognizedEntryException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

  /** The network itself. */
  private Network _network = new Network();
  private String _filename;
  private boolean _opened;
  
  public Network getNetwork() {
    return _network;
  }
  
  public void setFileName(String filename){
    _filename = filename;
  }

  public void changeNetwork(Network network){
    _network = network;
  }

  public boolean getOpened() {
    return _opened;
  }

  public void changeOpened() {
    _opened = true;
  }
  /**
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   * @throws ImportFileException
   */
  public void load(String filename) throws UnavailableFileException {
    ObjectInputStream obIn = null;
    try{
      FileInputStream fpin = new FileInputStream(filename);
      InflaterInputStream inflateIn = new InflaterInputStream(fpin);
      obIn = new ObjectInputStream(inflateIn);
      Network anObject = (Network) obIn.readObject();
      this.changeNetwork(anObject);
    } catch (ClassNotFoundException | IOException e) {
      throw new UnavailableFileException(filename);
    }finally{
      if (obIn != null){
        try {
          obIn.close();
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }
  
  /**
   * Saves the serialized application's state into the file associated to the current network.
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
    ObjectOutputStream obOut = null;
    try{
      FileOutputStream fpout = new FileOutputStream(_filename);
      DeflaterOutputStream dOut = new DeflaterOutputStream(fpout);
      obOut = new ObjectOutputStream(dOut);
      obOut.writeObject(_network);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }finally{
      if (obOut != null){
        try {
          obOut.close();
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }
  
  /**
   * Saves the serialized application's state into the specified file. The current network is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current network does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   */
  public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
    ObjectOutputStream obOut = null;
    try{
      FileOutputStream fpout = new FileOutputStream(filename);
      DeflaterOutputStream dOut = new DeflaterOutputStream(fpout);
      obOut = new ObjectOutputStream(dOut);
      obOut.writeObject(_network);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }finally{
      if (obOut != null){
        try {
          obOut.close();
        } catch (IOException e) {
          System.err.println(e.getMessage());
        }
      }
    }
  }
  
  /**
   * Read text input file and create domain entities..
   * 
   * @param filename name of the text input file
   * @throws ImportFileException
   */
  public void importFile(String filename) throws ImportFileException {
    try {
      _network.importFile(filename);
    } 
    catch (IOException | UnrecognizedEntryException /* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(filename, e);
    }
  }  
}
