package edu.ntnu.idi.idatt.utils;

import edu.ntnu.idi.idatt.model.Unit;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * A utility class for handling the user input from the console and validates it
 * This class collects and validates user input, prompting until valid input is received..
 *
 * <p>Supports input of:</p>
 * <ul>
 *   <li>Strings</li>
 *   <li>Numbers (Double and Integer)</li>
 *   <li>Units</li>
 *   <li>Dates</li>
 * </ul>
 *
 * @author TriLe
 */
public class UserInputHandler {
  private final Scanner scanner;
  
  /**
   * Initializes a new UserInputHandler instance with the provided Scanner.
   *
   * @param scanner the Scanner instance to read user input from.
   */
  public UserInputHandler(Scanner scanner) {
    this.scanner = scanner;
  }
  
  /**
   * Prompts the user for a string input and validates that it is not null or empty.
   *
   * @param request the message to display to the user.
   * @param errorMessage the error message to display in case of invalid input.
   * @param fieldName the name of the field being validated, used for error reporting.
   * @return a valid non-null, non-empty string entered by the user.
   * @throws IllegalArgumentException if the user input is null or empty.
   */
  public String getValidatedString(String request, String errorMessage, String fieldName) {
    while (true) {
      System.out.println(request);
      try {
        String input = scanner.nextLine().trim();
        InputValidation.validateString(input, fieldName);
        return input;
      } catch (IllegalArgumentException e) {
        System.out.println(errorMessage + ": " + e.getMessage());
      }
    }
  }
  
  /**
   * Prompts the user for a double input and validates that it is non-negative.
   *
   * @param request the message to display to the user.
   * @param errorMessage the error message to display in case of invalid input.
   * @param fieldName the name of the field being validated, used for error reporting.
   * @return a valid non-negative double entered by the user.
   * @throws IllegalArgumentException if the user input is negative or invalid.
   */
  public double getValidatedDouble(String request, String errorMessage, String fieldName) {
    while (true) {
      System.out.println(request);
      try {
        double number = Double.parseDouble(scanner.next());
        InputValidation.validateDouble(number, fieldName);
        return number;
      } catch (IllegalArgumentException e) {
        System.out.println(errorMessage + ": " + e.getMessage());
      }
    }
  }
  
  /**
   * Prompts the user for an integer input and validates that it is non-negative.
   *
   * @param request the message to display to the user.
   * @param errorMessage the error message to display in case of invalid input.
   * @param fieldName the name of the field being validated, used for error reporting.
   * @return a valid non-negative integer entered by the user.
   * @throws IllegalArgumentException if the user input is negative or invalid.
   */
  public int getValidatedInt(String request, String errorMessage, String fieldName) {
    while (true) {
      System.out.println(request);
      try {
        int number = Integer.parseInt(scanner.next());
        InputValidation.validateInt(number, fieldName);
        return number;
      } catch (IllegalArgumentException e) {
        System.out.println(errorMessage + ": " + e.getMessage());
      }
    }
  }
  
  /**
   * Prompts the user for a unit input and validates that it matches a predefined {@link Unit}.
   *
   * @param request the message to display to the user.
   * @param errorMessage the error message to display in case of invalid input.
   * @return a valid {@link Unit} entered by the user.
   * @throws IllegalArgumentException if the user input does not match a valid unit.
   */
  public Unit getValidatedUnit(String request, String errorMessage) {
    while (true) {
      System.out.println(request);
      try {
        Unit unit = Unit.fromSymbol(scanner.next());
        InputValidation.validationEnum(unit);
        return unit;
      } catch (IllegalArgumentException e) {
        System.out.println(errorMessage + ": " + e.getMessage());
      }
    }
  }
  
  /**
   * Prompts the user for a date input and validates that it is not in the past.
   *
   * @param request the message to display to the user.
   * @param errorMessage the error message to display in case of invalid input.
   * @return a valid LocalDate entered by the user.
   * @throws DateTimeParseException if the user input is not in the expected format.
   * @throws IllegalArgumentException if the date is in the past or otherwise invalid.
   */
  public LocalDate getValidatedDate(String request, String errorMessage) {
    while (true) {
      System.out.println(request);
      try {
        LocalDate localDate = LocalDate.parse(scanner.next());
        InputValidation.validateDateInThePast(localDate, false);
        return localDate;
      } catch (DateTimeParseException e) {
        System.out.println(errorMessage
            + ": Invalid date format. Please enter in yyyy-mm-dd format.");
      } catch (IllegalArgumentException e) {
        System.out.println(errorMessage + ": " + e.getMessage());
      }
    }
  }
}
