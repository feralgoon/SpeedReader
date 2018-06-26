package controllers;

import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import javax.inject.Inject;

public class AccountController extends Controller
{
    private FormFactory formFactory;

    @Inject
    public AccountController(FormFactory formFactory)
    {
        this.formFactory = formFactory;
    }

    public Result getSignup()
    {
        return ok(signup.render());
    }
}
