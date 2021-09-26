package rs.raf.dmilutinovic10518rn.wpseptembar.middleware;

import rs.raf.dmilutinovic10518rn.wpseptembar.resources.ProtectedCategoryResource;
import rs.raf.dmilutinovic10518rn.wpseptembar.resources.ProtectedNewsResource;
import rs.raf.dmilutinovic10518rn.wpseptembar.resources.UserResource;
import rs.raf.dmilutinovic10518rn.wpseptembar.services.IUserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthMiddleware implements ContainerRequestFilter {

    @Inject
    IUserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!this.isAuthRequired(requestContext))
            return;

        try {
            String token = requestContext.getHeaderString("Authorization");

            if (token != null && token.startsWith("Bearer "))
                token = token.replace("Bearer ", "");

            if (!this.userService.isLoggedIn(token))
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            else {
                if (this.isAdminArea(requestContext) && !this.userService.hasRole(token, "admin"))
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            exception.printStackTrace();

            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof UserResource || matchedResource instanceof ProtectedNewsResource || matchedResource instanceof ProtectedCategoryResource)
                return true;
        }

        return false;
    }

    private boolean isAdminArea(ContainerRequestContext req) {
        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof UserResource)
                return true;
        }

        return false;
    }
}
