package si.fri.rso.smartarticle.articles.api.v1.resources;

import si.fri.rso.smartarticle.articles.models.entities.Article;
import si.fri.rso.smartarticle.articles.services.beans.ArticlesBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@RequestScoped
@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticlesResource {

    @Inject
    private ArticlesBean articlesBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getArticles() {

        List<Article> articles = articlesBean.getArticles(uriInfo);

        return Response.ok(articles).build();
    }

    @GET
    @Path("/filtered")
    public Response getArticlesFiltered() {

        List<Article> articles;

        articles = articlesBean.getArticlesFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(articles).build();
    }

    @GET
    @Path("/{articleId}")
    public Response getArticle(@PathParam("articleId") Integer articleId) {

        Article article = articlesBean.getArticle(articleId);

        if (article == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(article).build();
    }
}
