package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscription
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionId;

    private int patronId;
    private int sourceId;

    public int getPatronId()
    {
        return patronId;
    }

    public void setPatronId(int patronId)
    {
        this.patronId = patronId;
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
