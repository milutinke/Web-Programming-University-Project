package rs.raf.dmilutinovic10518rn.wpseptembar.middleware;

import org.glassfish.jersey.server.validation.ValidationError;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Provider
public class ValidationExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        ArrayList<ValidationError> errors = new ArrayList<>();
        errors.add(toValidationError(e));

        HashMap<String, List<ValidationError>> errorsMap = new HashMap<>() {{
            put("errors", errors);
        }};

        return Response.status(Response.Status.BAD_REQUEST).entity(errorsMap)
                .type(MediaType.APPLICATION_JSON).build();
    }

    private ValidationError toValidationError(ValidationException e) {
        ValidationError error = new ValidationError();
        error.setMessage(e.getMessage());
        return error;
    }
}
