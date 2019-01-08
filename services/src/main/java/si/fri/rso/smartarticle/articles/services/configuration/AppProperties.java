package si.fri.rso.smartarticle.articles.services.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("app-properties")
public class AppProperties {

    @ConfigValue(value = "article-services.enabled", watch = true)
    private boolean articleServicesEnabled;

    public boolean isArticleServicesEnabled() {
        return articleServicesEnabled;
    }

    public void setArticleServicesEnabled(boolean articleServicesEnabled) {
        this.articleServicesEnabled = articleServicesEnabled;
    }
}
