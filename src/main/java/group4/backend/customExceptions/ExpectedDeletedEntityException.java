package group4.backend.customExceptions;

/**
 * Exception thrown when an entity that was expected to be deleted still exists in the system.
 * <p>
 * NOTE: Java documentation was generated with help from AI to ensure it follows Java documentation guidelines.
 */
public class ExpectedDeletedEntityException extends RuntimeException {
  /**
   * Constructs a new ExpectedDeletedEntityException with the specified error message.
   *
   * @param message the message explaining why the exception was thrown
   */
  public ExpectedDeletedEntityException(String message) {
    super(message);
  }
}
