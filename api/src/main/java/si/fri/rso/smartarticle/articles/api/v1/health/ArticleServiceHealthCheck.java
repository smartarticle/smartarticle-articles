package si.fri.rso.smartarticle.articles.api.v1.health;

import si.fri.rso.smartarticle.articles.services.configuration.AppProperties;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class ArticleServiceHealthCheck implements HealthCheck{
    @Inject
    private AppProperties appProperties;

    public HealthCheckResponse call() {
        if (appProperties.isHealthy()) {
            return  HealthCheckResponse.named(ArticleServiceHealthCheck.class.getSimpleName()).up().build();
        } else {
            return  HealthCheckResponse.named(ArticleServiceHealthCheck.class.getSimpleName()).down().build();
        }
    }
}
