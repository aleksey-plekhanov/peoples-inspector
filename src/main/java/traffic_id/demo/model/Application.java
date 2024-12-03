package traffic_id.demo.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_application;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Заявитель", nullable = false, unique=false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Данные", nullable = false, unique=true)
    private ApplicationData application_data;

    @Column(name = "Время поступления", nullable = true, columnDefinition="date")
    private Date date_arrive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Район", nullable = false, unique=false)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Статус", nullable = false, unique=false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Модератор", nullable = true, unique=false)
    private Moderator moderator;

    @Column(name="Комментарий модератора", nullable = false, columnDefinition="text")
    private String commentary;

    @Column(name = "Время проверки", nullable = true, columnDefinition="date")
    private Date date_check;

    public Application(Integer id_application, User user, ApplicationData application_data, Date date_arrive,
            District district, Status status, Moderator moderator, String commentary, Date date_check) {
        this.id_application = id_application;
        this.user = user;
        this.application_data = application_data;
        this.date_arrive = date_arrive;
        this.district = district;
        this.status = status;
        this.moderator = moderator;
        this.commentary = commentary;
        this.date_check = date_check;
    }

    public Application() {
    }

    public Integer getId_application() {
        return id_application;
    }

    public void setId_application(Integer id_application) {
        this.id_application = id_application;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApplicationData getApplication_data() {
        return application_data;
    }

    public void setApplication_data(ApplicationData application_data) {
        this.application_data = application_data;
    }

    public Date getDate_arrive() {
        return date_arrive;
    }

    public void setDate_arrive(Date date_arrive) {
        this.date_arrive = date_arrive;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Moderator getModerator() {
        return moderator;
    }

    public void setModerator(Moderator moderator) {
        this.moderator = moderator;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Date getDate_check() {
        return date_check;
    }

    public void setDate_check(Date date_check) {
        this.date_check = date_check;
    }

    @Override
    public String toString() {
        return "Application [id_application=" + id_application + ", user=" + user + ", application_data="
                + application_data + ", date_arrive=" + date_arrive + ", district=" + district + ", status=" + status
                + ", moderator=" + moderator + ", commentary=" + commentary + ", date_check=" + date_check + "]";
    }
}
