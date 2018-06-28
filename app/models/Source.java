package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Source
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int sourceId;

    private String sourceName;
    private String sourceLink;

    public int getSourceId()
    {
        return sourceId;
    }

    public String getSourceName()
    {
        return sourceName;
    }

    public void setSourceName(String sourceName)
    {
        this.sourceName = sourceName;
    }

    public String getSourceLink()
    {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink)
    {
        this.sourceLink = sourceLink;
    }
}
