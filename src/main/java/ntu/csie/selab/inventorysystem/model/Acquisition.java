package ntu.csie.selab.inventorysystem.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Acquisition")
public class Acquisition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type")
    private AcquisitionType type;
    @Column(name = "donor")
    private String donor;
    @Column(name = "contact")
    private String contact;
    @Column(name = "phone")
    private String phone;
    @Column(name = "date")
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status")
    private AcquisitionStatus status;

    public Integer getId() {
        return id;
    }
    public AcquisitionType getType() {
        return type;
    }
    public String getDonor() {
        return donor;
    }
    public String getContact() {
        return contact;
    }
    public String getPhone() {
        return phone;
    }
    public Date getDate() {
        return date;
    }
    public AcquisitionStatus getStatus() {
        return status;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setType(AcquisitionType type) {
        this.type = type;
    }
    public void setDonor(String donor) {
        this.donor = donor;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setStatus(AcquisitionStatus status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return String.format("Acquisition[id=%d, type='%s', donor='%s', contact='%s', phone='%s', date='%s', status='%s']",
                id, type.toString(), donor, contact, phone, date.toString(), status.toString());
    }
}
