package si.fri.rso.smartarticle.articles.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "article")
@NamedQueries(value =
        {
                @NamedQuery(name = "Article.getAll", query = "SELECT c FROM article c")
        })
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String abstraction;

    private String url;

    private Instant creation;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getAbstraction() {
        return abstraction;
    }

    public void setAbstraction(String abstraction) {
        this.abstraction = abstraction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getCreation() {
        return creation;
    }

    public void setCreation(Instant creation) {
        this.creation = creation;
    }


}