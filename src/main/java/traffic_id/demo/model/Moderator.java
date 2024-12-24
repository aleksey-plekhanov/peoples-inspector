package traffic_id.demo.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "moderator")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Moderator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moderator")
    private Integer moderatorId;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Пользователь", nullable = false, unique=false)
    private User user;

    @Column(name = "Дата начала должности", nullable = false, columnDefinition="date")
    private Date beginPost;

    @Column(name = "Дата окончания должности", nullable = true, columnDefinition="date")
    private Date endPost;

    public Moderator(Integer moderatorId, User user, Date beginPost, Date endPost) {
        this.moderatorId = moderatorId;
        this.user = user;
        this.beginPost = beginPost;
        this.endPost = endPost;
    }

    public Moderator() {
    }

    public Integer getId() {
        return moderatorId;
    }

    public void setId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBeginPost() {
        return beginPost;
    }

    public void setBeginPost(Date beginPost) {
        this.beginPost = beginPost;
    }

    public Date getEndPost() {
        return endPost;
    }

    public void setEndPost(Date endPost) {
        this.endPost = endPost;
    }

    @Override
    public String toString() {
        return "Moderator [moderatorId=" + moderatorId + ", user=" + user + ", beginPost=" + beginPost
                + ", endPost=" + endPost + "]";
    }
    
}
