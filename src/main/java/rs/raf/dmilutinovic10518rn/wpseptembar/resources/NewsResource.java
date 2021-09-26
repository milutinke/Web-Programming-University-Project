package rs.raf.dmilutinovic10518rn.wpseptembar.resources;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.News;
import rs.raf.dmilutinovic10518rn.wpseptembar.entities.PaginatedRecord;
import rs.raf.dmilutinovic10518rn.wpseptembar.services.INewsService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/news")
public class NewsResource {
    @Inject
    private INewsService newsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{page}")
    public PaginatedRecord getNews(@PathParam("page") int page) {
        return newsService.getNews(page);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/single/{id}")
    public News getSingle(@Context HttpServletRequest req, @PathParam("id") int id) {
        return newsService.getSingle(req.getRemoteAddr(), id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{page}")
    public PaginatedRecord search(@PathParam("page") int page, @QueryParam("query") String query) {
        return newsService.searchNews(page, query);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/home-page")
    public List<News> getHomePageNews() {
        return newsService.getHomePageNews();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/most-read")
    public List<News> getMostReadNews() {
        return newsService.getMostReadNews();
    }
}
