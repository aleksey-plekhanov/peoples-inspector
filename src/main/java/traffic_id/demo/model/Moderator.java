package traffic_id.demo.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "moderator")
public class Moderator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_moderator;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Пользователь", nullable = false, unique=true)
    private User user;

    @Column(name = "Дата начала должности", nullable = false, columnDefinition="date")
    private Date begin_post;

    @Column(name = "Дата окончания должности", nullable = false, columnDefinition="date")
    private Date end_post;

    public Moderator(Integer id_moderator, User user, Date begin_post, Date end_post) {
        this.id_moderator = id_moderator;
        this.user = user;
        this.begin_post = begin_post;
        this.end_post = end_post;
    }

    public Moderator() {
    }

    public Integer getId() {
        return id_moderator;
    }

    public void setId(Integer id_moderator) {
        this.id_moderator = id_moderator;
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

    @Override
    public String toString() {
        return "Moderator [id_moderator=" + id_moderator + ", user=" + user + ", begin_post=" + begin_post
                + ", end_post=" + end_post + "]";
    }
    
}
