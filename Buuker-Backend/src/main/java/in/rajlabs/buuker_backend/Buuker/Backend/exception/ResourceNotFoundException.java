package in.rajlabs.buuker_backend.Buuker.Backend.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message Detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
