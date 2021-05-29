package entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private LocalDateTime timestamp;
    private Response.StatusType status;
    private String message;
    private List<String> details;

    public ApiError() {}

    public Response.StatusType getStatus() {
        return status;
    }

    public void setStatus(Response.StatusType status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}