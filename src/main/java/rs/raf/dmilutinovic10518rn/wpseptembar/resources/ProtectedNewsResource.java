package rs.raf.dmilutinovic10518rn.wpseptembar.resources;

import rs.raf.dmilutinovic10518rn.wpseptembar.entities.News;
import rs.raf.dmilutinovic10518rn.wpseptembar.services.INewsService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/news")
public class ProtectedNewsResource {
    @Inject
    private INewsService newsService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/post")
    public News postNews(@Valid News news) {
        return newsService.postNews(news);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public boolean editNews(@PathParam("id") int id, @Valid News news) {
        news.setId(id);
        return newsService.editNews(news);
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public boolean deleteNews(@PathParam("id") int id) {
        return newsService.deleteNews(id);
    }
}
