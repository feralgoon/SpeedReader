package models;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Patron
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int patronId;

    private String username;
    private String email;
    private Timestamp accountCreated;
    private byte[] password;
    private byte[] salt;

    public int getPatronId()
    {
        return patronId;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public void setSalt(byte[] salt)
    {
        this.salt = salt;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Timestamp getAccountCreated()
    {
        return accountCreated;
    }

    public void setAccountCreated(Timestamp accountCreated)
    {
        this.accountCreated = accountCreated;
    }

    public byte[] getPassword()
    {
        return password;
    }

    public void setPassword(byte[] password)
    {
        this.password = password;
    }
}
