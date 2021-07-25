package codefellowship.demo.Domain;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
public class Post {
    // Add a Post entity to your app.
    // A Post has a body and a createdAt timestamp.
    // A logged-in user should be able to create a Post, and a post should belong to the user that created it.
    // hint: this is a relationship between two pieces of data

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    Long id ;

    String body;
    Timestamp createdAt;

    @ManyToOne
    AppUser appUser;

    public Post() {
    }

    public Post(String body, Timestamp createdAt, AppUser appUser) {
        this.body = body;
        this.createdAt = createdAt;
        this.appUser = appUser;
    }

    public Long getId() {
        return id;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
