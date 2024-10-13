package in.rajlabs.buuker_backend.Buuker.Backend.dto; // Adjust package as necessary

import java.util.Optional;

/**
 * Generic class representing the result of an operation.
 *
 * @param <T> The type of the optional data associated with the operation result.
 */
public class Result<T> {
    private boolean success;            // Indicates if the operation was successful
    private String message;             // Message related to the operation result
    private Optional<T> data;           // Optional data associated with the operation result

    // Constructor for success result with data
    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = Optional.ofNullable(data);
    }

    // Constructor for success result without data
    public Result(boolean success, String message) {
        this(success, message, null); // Calls the main constructor with null data
    }

    // Getters
    public boolean isSuccess() {
        return success; // Returns the success status
    }

    public String getMessage() {
        return message; // Returns the message
    }

    public Optional<T> getData() {
        return data; // Returns the optional data
    }
}
