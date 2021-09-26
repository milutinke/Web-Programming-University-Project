package rs.raf.dmilutinovic10518rn.wpseptembar.middleware;

import org.glassfish.jersey.server.validation.ValidationError;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ValidationError> errors = e.getConstraintViolations().stream()
                .map(this::toValidationError)
                .collect(Collectors.toList());

        HashMap<String, List<ValidationError>> errorsMap = new HashMap<>() {{
            put("errors", errors);
        }};

        return Response.status(Response.Status.BAD_REQUEST).entity(errorsMap)
                .type(MediaType.APPLICATION_JSON).build();
    }

    private ValidationError toValidationError(ConstraintViolation constraintViolation) {
        ValidationError error = new ValidationError();
        error.setPath(extractPropertyName(constraintViolation.getPropertyPath().toString()));
        error.setMessage(constraintViolation.getMessage());
        return error;
    }

    private String extractPropertyName(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
