package in.rajlabs.buuker_backend.Buuker.Backend.util; // Adjust package as necessary

import java.time.Instant;
import java.util.HashMap; // Import HashMap for the response structure
import java.util.Date; // Import Date for timestamps

/**
 * Helper class for generating standardized JSON response maps.
 */
public class ResponseHelper {

    /**
     * Generates a success response map.
     *
     * @param message The success message
     * @return A HashMap containing the success response
     */
    public static HashMap<String, Object> createSuccessResponse(String message) {
        HashMap<String, Object> response = new HashMap<>();
        response.put(Constants.KEY_SUCCESS, true); // Indicates success
        response.put(Constants.KEY_MESSAGE, message); // Success message
        return response;
    }

    /**
     * Generates a success response map with additional data.
     *
     * @param message The success message
     * @param data    Additional data to include in the response
     * @return A HashMap containing the success response
     */
    public static HashMap<String, Object> createSuccessResponse(String message, Object data) {
        HashMap<String, Object> response = createSuccessResponse(message);
        response.put(Constants.KEY_DATA, data); // Include additional data
        return response;
    }

    /**
     * Generates a success response map with a timestamp.
     *
     * @param message The success message
     * @return A HashMap containing the success response with timestamp
     */
    public static HashMap<String, Object> createSuccessResponseWithTimestamp(String message) {
        HashMap<String, Object> response = createSuccessResponse(message);
        response.put(Constants.KEY_TIMESTAMP, Instant.now().toEpochMilli()); // Add timestamp
        return response;
    }

    /**
     * Generates a success response map with a timestamp and additional data.
     *
     * @param message The success message
     * @param data    Additional data to include in the response
     * @return A HashMap containing the success response with timestamp
     */
    public static HashMap<String, Object> createSuccessResponseWithTimestamp(String message, Object data) {
        HashMap<String, Object> response = createSuccessResponseWithTimestamp(message);
        response.put(Constants.KEY_DATA, data); // Include additional data
        return response;
    }

    /**
     * Generates an error response map.
     *
     * @param message The error message
     * @return A HashMap containing the error response
     */
    public static HashMap<String, Object> createErrorResponse(String message) {
        HashMap<String, Object> response = new HashMap<>();
        response.put(Constants.KEY_SUCCESS, false); // Indicates failure
        response.put(Constants.KEY_MESSAGE, message); // Error message
        return response;
    }

    /**
     * Generates an error response map with a timestamp.
     *
     * @param message The error message
     * @return A HashMap containing the error response with timestamp
     */
    public static HashMap<String, Object> createErrorResponseWithTimestamp(String message) {
        HashMap<String, Object> response = createErrorResponse(message);
        response.put(Constants.KEY_TIMESTAMP, new Date()); // Add timestamp
        return response;
    }

    /**
     * Updates the message in an existing response map.
     *
     * @param response   The existing response map
     * @param newMessage The new message to update
     * @return The updated response map
     */
    public static HashMap<String, Object> updateMessageInResponse(HashMap<String, Object> response, String newMessage) {
        response.put(Constants.KEY_MESSAGE, newMessage); // Update the message
        return response; // Return the updated response
    }
}
