package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CommentDetail
{
    @Id
    private int patronCommentId;

    private String username;
    private String comment;
    private Date date;

    public CommentDetail(int patronCommentId, String username, String comment, Date date)
    {
        this.patronCommentId = patronCommentId;
        this.username = username;
        this.comment = comment;
        this.date = date;
    }

    public int getPatronCommentId()
    {
        return patronCommentId;
    }

    public String getUsername()
    {
        return username;
    }

    public String getComment()
    {
        return comment;
    }

    public Date getDate()
    {
        return date;
    }
}
