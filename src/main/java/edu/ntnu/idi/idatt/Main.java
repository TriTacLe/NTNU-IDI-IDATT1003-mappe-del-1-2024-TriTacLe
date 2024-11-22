package edu.ntnu.idi.idatt;

/**
 * The main class.
 */
public class Main {
  
  /**
   * Main method.
   *
   * @param args args.
   */
  public static void main(String[] args) {
    UserInterface userInterface = new UserInterface();
    userInterface.init();
    userInterface.start();
  }
}
