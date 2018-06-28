package controllers;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import models.Password;
import models.Patron;
import models.Source;
import models.Subscription;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class AccountController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public AccountController(FormFactory formFactory, JPAApi jpaApi)
    {
        this.formFactory = formFactory;
        this.jpaApi      = jpaApi;
    }

    public Result getSignup()
    {
        return ok(signup.render(""));
    }

    @Transactional
    public Result postSignup()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        String username      = form.get("username");
        String email         = form.get("email");
        String password      = form.get("password");
        String passwordAgain = form.get("passwordAgain");

        String sql = "SELECT p FROM Patron p " +
                        "WHERE username = :username";

        List<Patron> checkForAccount = jpaApi.em().createQuery(sql,Patron.class).setParameter("username",username).getResultList();

        if (checkForAccount.size() != 0)
        {
            return ok(signup.render("username"));
        }

        sql = "SELECT p FROM Patron p " +
                "WHERE email = :email";
        checkForAccount = jpaApi.em().createQuery(sql,Patron.class).setParameter("email",email).getResultList();

        if (checkForAccount.size() != 0)
        {
            return ok(signup.render("email"));
        }

        if (!password.equals(passwordAgain))
        {
            return ok(signup.render("password"));
        }

        Patron patron = new Patron();

        try
        {
            byte[] salt = Password.getNewSalt();
            byte[] hashedPassword = Password.hashPassword(password.toCharArray(), salt);
            password = null;

            patron.setSalt(salt);
            patron.setPassword(hashedPassword);
            patron.setAccountCreated(new Timestamp(System.currentTimeMillis()));
            patron.setEmail(email);
            patron.setUsername(username);

            jpaApi.em().persist(patron);
        }
        catch (Exception e)
        {
            return ok("Failed to save user");
        }
        session().put("User",username);
        return ok(home.render());
    }

    public Result getLogin()
    {
        return ok(login.render(""));
    }

    @Transactional
    public Result postLogin()
    {
        DynamicForm form = formFactory.form().bindFromRequest();

        String username = form.get("username");
        String password = form.get("password");

        String sql = "SELECT p FROM Patron p " +
                        "WHERE username = :username";

        List<Patron> patrons = jpaApi.em().createQuery(sql,Patron.class).setParameter("username",username).getResultList();

        if(patrons.size() == 1)
        {
            Patron patron = patrons.get(0);

            byte[] salt = patron.getSalt();
            byte[] hashedPassword = Password.hashPassword(password.toCharArray(),salt);

            if(Arrays.equals(hashedPassword,patron.getPassword()))
            {
                session().put("User",username);
                sql = "SELECT s FROM Source s ";
                List<Source> sourceList = jpaApi.em().createQuery(sql,Source.class).getResultList();
                return ok(user.render(sourceList));
            }
        }

        return ok(login.render("error"));
    }

    public Result getLogout()
    {
        session().clear();
        return ok(home.render());
    }

    @Transactional(readOnly = true)
    public Result getUser()
    {
        String sql = "SELECT s FROM Source s ";
        List<Source> sourceList = jpaApi.em().createQuery(sql,Source.class).getResultList();

        return ok(user.render(sourceList));
    }

    @Transactional
    public Result postUser()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sourceLink = form.get("newFeed");

        String sql = "SELECT p FROM Patron p " +
                        "WHERE p.username = :currentUser";
        Patron patron = jpaApi.em().createQuery(sql,Patron.class).setParameter("currentUser",session().get("User")).getSingleResult();

        if(sourceLink == null)
        {
            //System.out.println(form.get("subscribe"));

            String[] subscriptions = request().body().asMultipartFormData().asFormUrlEncoded().get("subscribe[]");
            for(String s : subscriptions)
            {
                System.out.println(s);
                Subscription sub = new Subscription();
                sub.setPatronId(patron.getPatronId());
                sub.setSourceId(Integer.parseInt(s));
                jpaApi.em().persist(sub);
            }
        }
        else
        {
            sql = "SELECT s FROM Source s " +
                    "WHERE sourceLink = :sourceLink";
            List<Source> sourceList = jpaApi.em().createQuery(sql, Source.class).setParameter("sourceLink", sourceLink).getResultList();

            if (sourceList.size() == 1)
            {
                Source source = sourceList.get(0);
                Subscription subscription = new Subscription();
                subscription.setPatronId(patron.getPatronId());
                subscription.setSourceId(source.getSourceId());
                jpaApi.em().persist(subscription);
            }
            else
            {
                try
                {
                    URL linkUrl = new URL(sourceLink);
                    SyndFeedInput input = new SyndFeedInput();
                    SyndFeed feed = input.build(new XmlReader(linkUrl));

                    Source newSource = new Source();
                    newSource.setSourceLink(sourceLink);
                    newSource.setSourceName(feed.getTitle());

                    jpaApi.em().persist(newSource);

                    Subscription subscription = new Subscription();
                    subscription.setSourceId(newSource.getSourceId());
                    subscription.setPatronId(patron.getPatronId());
                    jpaApi.em().persist(subscription);

                    System.out.println("Source Added");

                } catch (Exception e)
                {
                    System.out.println("Error adding Feed: " + sourceLink);
                }
            }
        }
        sql = "SELECT s FROM Source s ";
        List<Source> sourceList = jpaApi.em().createQuery(sql,Source.class).getResultList();

        return ok(user.render(sourceList));
    }
}
