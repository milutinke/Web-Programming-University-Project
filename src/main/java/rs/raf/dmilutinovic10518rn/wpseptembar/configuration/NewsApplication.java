package rs.raf.dmilutinovic10518rn.wpseptembar.configuration;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.dmilutinovic10518rn.wpseptembar.repositories.*;
import rs.raf.dmilutinovic10518rn.wpseptembar.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class NewsApplication extends ResourceConfig {
    public static final String JWT_SECRET = "myfandomsecret123";

    public NewsApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(UserService.class).to(IUserService.class).to(Singleton.class);
                this.bind(UserRepository.class).to(IUserRepository.class).to(Singleton.class);
                this.bind(NewsService.class).to(INewsService.class).to(Singleton.class);
                this.bind(NewsRepository.class).to(INewsRepository.class).to(Singleton.class);
                this.bind(CategoryRepository.class).to(ICategoryRepository.class).to(Singleton.class);
                this.bind(CategoryService.class).to(ICategoryService.class).to(Singleton.class);
                this.bind(CommentRepository.class).to(ICommentsRepository.class).to(Singleton.class);
                this.bind(CommentService.class).to(ICommentService.class).to(Singleton.class);
            }
        });

        packages("rs.raf.dmilutinovic10518rn.wpseptembar");
    }
}