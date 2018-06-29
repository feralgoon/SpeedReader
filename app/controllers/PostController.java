package controllers;

import models.Post;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.post;

import javax.inject.Inject;

public class PostController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public PostController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi      = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getPost(Integer id)
    {
        String sql = "SELECT p FROM Post p " +
                        "WHERE p.postId = :id";
        Post currentPost = jpaApi.em().createQuery(sql,Post.class).setParameter("id",id).getSingleResult();

        return ok(post.render(currentPost));
    }
}
