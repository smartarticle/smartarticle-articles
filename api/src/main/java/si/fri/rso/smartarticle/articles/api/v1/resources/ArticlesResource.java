package si.fri.rso.smartarticle.articles.api.v1.resources;


import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.smartarticle.articles.models.entities.Article;
import si.fri.rso.smartarticle.articles.models.entities.Pubmed;
import si.fri.rso.smartarticle.articles.services.beans.ArticlesBean;
import si.fri.rso.smartarticle.articles.services.configuration.AppProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;


@Log
@ApplicationScoped
@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticlesResource {

    @Inject
    private ArticlesBean articlesBean;

    @Context
    protected UriInfo uriInfo;

    @Inject
    private AppProperties appProperties;

    @GET
    public Response getArticles() {
        if (appProperties.isArticleServicesEnabled()) {
            List<Article> articles = articlesBean.getArticles(uriInfo);
            appProperties.setHealthy(true);
            return Response.ok(articles).build();
        }
        else{
            appProperties.setHealthy(true);
            return Response.ok().build();
        }
    }

    @GET
    @Path("/filtered")
    public Response getArticlesFiltered() {
        if (appProperties.isArticleServicesEnabled()) {
            List<Article> articles;

            articles = articlesBean.getArticlesFilter(uriInfo);
            appProperties.setHealthy(true);
            return Response.status(Response.Status.OK).entity(articles).build();
        }
        else{
            appProperties.setHealthy(true);
            return Response.ok().build();
        }
    }

    @GET
    @Path("/{articleId}")
    public Response getArticle(@PathParam("articleId") Integer articleId) {
        if (appProperties.isArticleServicesEnabled()) {
            Article article = articlesBean.getArticle(articleId);

            if (article == null) {
                appProperties.setHealthy(true);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            appProperties.setHealthy(true);
            return Response.status(Response.Status.OK).entity(article).build();
        }
        else{
            appProperties.setHealthy(true);
            return Response.ok().build();
        }
    }

    @GET
    @Path("/pubmed/{articleId}")
    public Response getPubMed(@PathParam("articleId") String articleId) {
        if (appProperties.isArticleServicesEnabled()) {
            Pubmed pubmed = articlesBean.getPubmed(articleId);

            if (pubmed == null) {
                appProperties.setHealthy(true);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            appProperties.setHealthy(true);
            return Response.status(Response.Status.OK).entity(pubmed).build();
        }
        else{
            appProperties.setHealthy(true);
            return Response.ok().build();
        }
    }
}
