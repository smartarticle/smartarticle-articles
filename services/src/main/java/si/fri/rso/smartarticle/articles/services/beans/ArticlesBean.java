package si.fri.rso.smartarticle.articles.services.beans;


import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.smartarticle.articles.models.entities.Article;
import si.fri.rso.smartarticle.articles.models.entities.Pubmed;
import si.fri.rso.smartarticle.articles.services.configuration.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.Optional;


@RequestScoped
public class ArticlesBean {

    private Logger log = Logger.getLogger(ArticlesBean.class.getName());

    @Inject
    private EntityManager em;

    private Client httpClient;

    @Inject
    private AppProperties appProperties;


    @PostConstruct
    private void init() { httpClient = ClientBuilder.newClient(); }


    public List<Article> getArticles(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();
        return JPAUtils.queryEntities(em, Article.class, queryParameters);
    }

    public List<Article> getArticlesFilter(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Article.class, queryParameters);
    }

    public Article getArticle(Integer articleId) {

        Article article = em.find(Article.class, articleId);

        if (article == null) {
            appProperties.setHealthy(false);
            throw new NotFoundException();
        }

        return article;
    }

    public Pubmed getPubmed(String id){
        Pubmed pubmed = new Pubmed();
        pubmed.setAbstraction(httpClient
                .target("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&retmode=json&rettype=abstract&id=" + id)
                .request().get().readEntity(String.class));
        return pubmed;
    }
}
