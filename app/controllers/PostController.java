package controllers;

import models.CommentDetail;
import models.Patron;
import models.PatronComment;
import models.Post;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.post;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

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

        sql = "SELECT NEW models.CommentDetail(c.patronCommentId,p.username,c.commentContent,c.commentTime) " +
                "FROM PatronComment c " +
                "JOIN Patron p ON c.patronId = p.patronId " +
                "WHERE c.postId = :id " +
                "ORDER BY c.commentTime DESC";
        List<CommentDetail> comments = jpaApi.em().createQuery(sql,CommentDetail.class).setParameter("id",currentPost.getPostId()).getResultList();

        return ok(post.render(currentPost,comments));
    }

    @Transactional
    public Result postPost(Integer id)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String comment = form.get("comment");
        String user = session().get("User");

        String sql = "SELECT p FROM Patron p " +
                        "WHERE username = :user";
        Patron patron = jpaApi.em().createQuery(sql,Patron.class).setParameter("user",user).getSingleResult();
        PatronComment patronComment = new PatronComment();
        patronComment.setCommentContent(comment);
        patronComment.setPatronId(patron.getPatronId());
        patronComment.setPostId(id);
        patronComment.setCommentTime(new Timestamp(System.currentTimeMillis()));
        jpaApi.em().persist(patronComment);

        return getPost(id);
    }
}
