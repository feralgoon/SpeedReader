package controllers;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import play.mvc.*;

import views.html.*;

import java.net.URL;

public class HomeController extends Controller
{
    public Result index() throws Exception
    {
        //http://rss.cnn.com/rss/cnn_topstories.rss

        URL cnnTopStories = new URL("http://rss.cnn.com/rss/cnn_topstories.rss");

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(cnnTopStories));



        //System.out.println(feed);

        return ok(index.render());
    }

}
