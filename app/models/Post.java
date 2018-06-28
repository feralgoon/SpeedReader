package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Post
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int postId;

    private String postLink;
    private String postTitle;
    private String postDescription;
    private int sourceId;
    private Date date;

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getPostId()
    {
        return postId;
    }

    public String getPostLink()
    {
        return postLink;
    }

    public void setPostLink(String postLink)
    {
        this.postLink = postLink;
    }

    public String getPostTitle()
    {
        return postTitle;
    }

    public void setPostTitle(String postTitle)
    {
        this.postTitle = postTitle;
    }

    public String getPostDescription()
    {
        return postDescription;
    }

    public void setPostDescription(String postDescription)
    {
        this.postDescription = postDescription;
    }

    public int getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(int sourceId)
    {
        this.sourceId = sourceId;
    }
}
