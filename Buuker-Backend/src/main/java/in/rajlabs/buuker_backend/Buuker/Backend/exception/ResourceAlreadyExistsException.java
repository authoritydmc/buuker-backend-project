package in.rajlabs.buuker_backend.Buuker.Backend.exception;

/**
 * Exception thrown when a resource already exists.
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
