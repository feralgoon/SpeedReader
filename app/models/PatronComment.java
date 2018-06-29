package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PatronComment
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int patronCommentId;

    private Date commentTime;
    private String commentContent;
    private int postId;
    private int patronId;

    public int getPatronCommentId()
    {
        return patronCommentId;
    }

    public Date getCommentTime()
    {
        return commentTime;
    }

    public void setCommentTime(Date commentTime)
    {
        this.commentTime = commentTime;
    }

    public String getCommentContent()
    {
        return commentContent;
    }

    public void setCommentContent(String commentContent)
    {
        this.commentContent = commentContent;
    }

    public int getPostId()
    {
        return postId;
    }

    public void setPostId(int postId)
    {
        this.postId = postId;
    }

    public int getPatronId()
    {
        return patronId;
    }

    public void setPatronId(int patronId)
    {
        this.patronId = patronId;
    }
}
