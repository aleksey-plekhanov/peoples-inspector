package traffic_id.demo.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;

@Entity
@Table(name = "application")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Заявитель", nullable = false, unique=false)
    private User user;

    @Column(name = "Название", nullable = false, columnDefinition="varchar(50)")
    private String title;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Данные", nullable = false, unique=true)
    private ApplicationData applicationData;

    @Column(name = "Время поступления", nullable = true, columnDefinition="date")
    private Date dateArrive;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Район", nullable = false, unique=false)
    private District district;

    @Column(name = "Адрес", nullable = false, columnDefinition="varchar(100)")
    private String address;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Статус", nullable = false, unique=false)
    private Status status;

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Модератор", nullable = true, unique=false)
    private Moderator moderator;

    @Column(name="Комментарий модератора", nullable = true, columnDefinition="text")
    private String commentary;

    @Column(name = "Время проверки", nullable = true, columnDefinition="date")
    private Date dateCheck;

    public Application(Integer applicationId, User user, String title, ApplicationData applicationData, Date dateArrive,
            District district, String address, Status status, Moderator moderator, String commentary, Date dateCheck) {
        this.applicationId = applicationId;
        this.user = user;
        this.title = title;
        this.applicationData = applicationData;
        this.dateArrive = dateArrive;
        this.district = district;
        this.address = address;
        this.status = status;
        this.moderator = moderator;
        this.commentary = commentary;
        this.dateCheck = dateCheck;
    }

    public Application(User user, String title, ApplicationData applicationData, District district, String address, Status status) {
        this.user = user;
        this.title = title;
        this.applicationData = applicationData;
        this.district = district;
        this.address = address;
        this.status = status;
    }

    public Application() {
    }

    public Integer getIdapplication() {
        return applicationId;
    }

    public void setId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ApplicationData getData() {
        return applicationData;
    }

    public void setData(ApplicationData applicationData) {
        this.applicationData = applicationData;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Date getDateCheck() {
        return dateCheck;
    }

    public void setDateCheck(Date dateCheck) {
        this.dateCheck = dateCheck;
    }

    @Override
    public String toString() {
        return "Application [applicationId=" + applicationId + ", user=" + user + ", title=" + title + ", applicationData="
                + applicationData + ", dateArrive=" + dateArrive + ", district=" + district + ", address=" + address 
                + ", status=" + status + ", moderator=" + moderator + ", commentary=" + commentary + ", dateCheck=" + dateCheck + "]";
    }
}
