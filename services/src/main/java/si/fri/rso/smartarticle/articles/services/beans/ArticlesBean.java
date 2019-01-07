package si.fri.rso.smartarticle.articles.services.beans;


import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.smartarticle.articles.models.entities.Article;
import si.fri.rso.smartarticle.articles.services.configuration.AppProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.Optional;


@RequestScoped
public class ArticlesBean {

    private Logger log = Logger.getLogger(ArticlesBean.class.getName());

    @Inject
    private EntityManager em;

    @Inject
    private AppProperties appProperties;

    @Inject
    private ArticlesBean articlesBean;

    @PostConstruct
    private void init() {}

    @Inject
    @DiscoverService("smartarticle-articles")
    private Optional<String> baseUrl;

    public List<Article> getArticles(UriInfo uriInfo) {
        if (appProperties.isExternalServicesEnabled()) {

            QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                    .defaultOffset(0)
                    .build();
            return JPAUtils.queryEntities(em, Article.class, queryParameters);
        }
        return null;
    }

    public List<Article> getArticlesFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Article.class, queryParameters);
    }

    public Article getArticle(Integer articleId) {

        Article article = em.find(Article.class, articleId);

        if (article == null) {
            throw new NotFoundException();
        }

        return article;
    }

    public Article createArticle(Article article) {

        try {
            beginTx();
            em.persist(article);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return article;
    }

    public Article putArticle(String articleId, Article article) {

        Article c = em.find(Article.class, articleId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            article.setId(c.getId());
            article = em.merge(article);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return article;
    }

    public boolean deleteArticle(String articleId) {

        Article article = em.find(Article.class, articleId);

        if (article != null) {
            try {
                beginTx();
                em.remove(article);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }


    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
