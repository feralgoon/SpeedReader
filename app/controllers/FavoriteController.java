package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class FavoriteController extends Controller
{
    public Result getAjax(Integer id)
    {
        return ok("gotten");
    }

    public Result postAjax(Integer id)
    {
        return ok("posted");
    }
}
