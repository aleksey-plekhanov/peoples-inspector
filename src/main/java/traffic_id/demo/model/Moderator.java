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
    private Integer moderatorId;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Пользователь", nullable = false, unique=false)
    private User user;

    @Column(name = "Дата начала должности", nullable = true, columnDefinition="date")
    private Date begin_post;

    @Column(name = "Дата окончания должности", nullable = false, columnDefinition="date")
    private Date end_post;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "moderator")
    private Set<Application> applications = new LinkedHashSet<>();

    public Moderator(Integer id_moderator, User user, Date begin_post, Date end_post) {
        this.moderatorId = moderatorId;
        this.user = user;
        this.begin_post = begin_post;
        this.end_post = end_post;
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

    public Date getBegin_post() {
        return begin_post;
    }

    public void setBegin_post(Date begin_post) {
        this.begin_post = begin_post;
    }

    public Date getEnd_post() {
        return end_post;
    }

    public void setEnd_post(Date end_post) {
        this.end_post = end_post;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Moderator [moderatorId=" + moderatorId + ", user=" + user + ", begin_post=" + begin_post
                + ", end_post=" + end_post + "]";
    }
    
}
