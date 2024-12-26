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
    @Column(name = "id_application")
    private Integer applicationId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Заявитель", nullable = false, unique=false)
    private User user;

    @Column(name = "Название", nullable = false, columnDefinition="varchar(50)")
    private String title;

    @Column(name = "Информация", nullable = false, columnDefinition="text")
    private String information;

    @Column(name = "Время поступления", nullable = true, columnDefinition="date")
    private Date dateArrive;

    @Column(name = "Время нарушения", nullable = false, columnDefinition="date")
    private Date dateViolation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Район", nullable = false, unique=false)
    private District district;

    @Column(name = "Адрес", nullable = false, columnDefinition="varchar(100)")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Статус", nullable = false, unique=false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Модератор", nullable = true, unique=false)
    private Moderator moderator;

    @Column(name="Комментарий модератора", nullable = true, columnDefinition="text")
    private String commentary;

    @Column(name = "Время проверки", nullable = true, columnDefinition="date")
    private Date dateCheck;

    public Application(Integer applicationId, User user, String title, String information, Date dateArrive, Date dateViolation,
            District district, String address, Status status, Moderator moderator, String commentary, Date dateCheck) {
        this.applicationId = applicationId;
        this.user = user;
        this.title = title;
        this.information = information;
        this.dateArrive = dateArrive;
        this.dateViolation = dateViolation;
        this.district = district;
        this.address = address;
        this.status = status;
        this.moderator = moderator;
        this.commentary = commentary;
        this.dateCheck = dateCheck;
    }

    public Application(User user, String title, String information, Date dateViolation, District district, String address, Status status) {
        this.user = user;
        this.title = title;
        this.information = information;
        this.dateViolation = dateViolation;
        this.district = district;
        this.address = address;
        this.status = status;
    }

    public Application() {
    }

    public Integer getId() {
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public Date getDateViolation() {
        return dateViolation;
    }

    public void setDateViolation(Date dateViolation) {
        this.dateViolation = dateViolation;
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
        return "Application [applicationId=" + applicationId + ", user=" + user + ", title=" + title + ", information="
                + information + ", dateArrive=" + dateArrive + ", dateViolation=" + dateViolation + ", district=" + district + ", address=" + address 
                + ", status=" + status + ", moderator=" + moderator + ", commentary=" + commentary + ", dateCheck=" + dateCheck + "]";
    }
}
