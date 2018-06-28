package controllers;

import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.SyndFeedOutput;
import com.rometools.rome.io.XmlReader;
import models.Patron;
import models.Post;
import models.Source;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.*;

import play.twirl.api.Html;
import views.html.*;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeController extends Controller
{
    private JPAApi jpaApi;

    @Inject
    public HomeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result index() throws Exception
    {

        if(session().get("User") == null)
        {
            return ok(home.render());
        }

        String sql = "SELECT p FROM Patron p " +
                        "WHERE p.username = :currentUser";

        Patron currentUser = jpaApi.em().createQuery(sql,Patron.class).setParameter("currentUser",session().get("User")).getSingleResult();

        sql = "SELECT s.sourceLink FROM Source s " +
                        "JOIN Subscription sub ON s.sourceId = sub.sourceId " +
                        "WHERE sub.patronId = :patronId ";

        List<String> sources = jpaApi.em().createQuery(sql,String.class).setParameter("patronId",currentUser.getPatronId()).getResultList();

        URL url;
        SyndFeedInput input = new SyndFeedInput();
        List<SyndEntry> entries = new ArrayList<>();

        for(String s : sources)
        {
            url = new URL(s);
            SyndFeed feed = input.build(new XmlReader(url));
            entries.addAll(feed.getEntries());
            System.out.println("Added " + entries.size());


            try
            {
                for (SyndEntry entry : entries)
                {
                    Date date = entry.getPublishedDate();
                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
                    sql = "SELECT p FROM Post p " +
                            "WHERE postLink = :postLink ";
                    List<Post> posts = jpaApi.em().createQuery(sql, Post.class).setParameter("postLink", entry.getLink()).getResultList();
                    if (posts.size() == 0)
                    {

                        Post post = new Post();
                        post.setPostLink(entry.getLink());
                        post.setPostTitle(entry.getTitle());
                        post.setPostDescription(entry.getDescription().getValue());
                        post.setDate(sqlDate);

                        sql = "SELECT s FROM Source s " +
                                "WHERE sourceName = :source";
                        List<Source> sourceList = jpaApi.em().createQuery(sql, Source.class).setParameter("source", feed.getTitle()).getResultList();
                        if (sourceList.size() != 0)
                        {
                            post.setSourceId(sourceList.get(0).getSourceId());
                        }
                        else
                        {
                            Source newSource = new Source();
                            newSource.setSourceName(feed.getTitle());
                            newSource.setSourceLink(feed.getLink());
                            jpaApi.em().persist(newSource);

                            post.setSourceId(newSource.getSourceId());

                            System.out.println("Source added. " + newSource.getSourceName());
                        }
                        jpaApi.em().persist(post);
                    }

                }
            } catch (Exception e)
            {
                System.out.println("Error adding post.");
            }
            entries.clear();
        }
        sql = "SELECT p FROM Post p " +
                "ORDER BY Date DESC ";
        List<Post> postList = jpaApi.em().createQuery(sql,Post.class).setMaxResults(30).getResultList();

        StringBuilder testBuilder = new StringBuilder();
        testBuilder.append("<div class=\"container\"><div class=\"row\"><div class=\"scrolldiv col-md-10 offset-md-1\">");
        for(Post post : postList)
        {
            testBuilder.append("<div class=\"row border border-success\">");
            testBuilder.append("<a href=\"");
            testBuilder.append(post.getPostLink());
            testBuilder.append("\">");
            testBuilder.append(post.getPostTitle());
            testBuilder.append("</a><br>");
            testBuilder.append(post.getPostDescription());
            testBuilder.append("<br>");
            testBuilder.append(post.getDate());
            testBuilder.append("</div><br>");
        }
        testBuilder.append("</div></div></div>");
        //output.output(feed,new PrintWriter(System.out));


        //System.out.println(feed);

        return ok(index.render(Html.apply(testBuilder.toString())));
    }

}
