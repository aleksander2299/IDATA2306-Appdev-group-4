package group4.backend.customExceptions;

public class ExpectedDeletedEntityException extends RuntimeException {
  public ExpectedDeletedEntityException(String message) {
    super(message);
  }
}
