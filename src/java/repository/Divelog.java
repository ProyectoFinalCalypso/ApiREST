/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "divelog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Divelog.findAll", query = "SELECT d FROM Divelog d"),
    @NamedQuery(name = "Divelog.findByIdDivelog", query = "SELECT d FROM Divelog d WHERE d.idDivelog = :idDivelog"),
    @NamedQuery(name = "Divelog.findByNumDive", query = "SELECT d FROM Divelog d WHERE d.numDive = :numDive"),
    @NamedQuery(name = "Divelog.findByStartDateTime", query = "SELECT d FROM Divelog d WHERE d.startDateTime = :startDateTime"),
    @NamedQuery(name = "Divelog.findByDuration", query = "SELECT d FROM Divelog d WHERE d.duration = :duration"),
    @NamedQuery(name = "Divelog.findByMaxDepth", query = "SELECT d FROM Divelog d WHERE d.maxDepth = :maxDepth"),
    @NamedQuery(name = "Divelog.findByAvgDepth", query = "SELECT d FROM Divelog d WHERE d.avgDepth = :avgDepth"),
    @NamedQuery(name = "Divelog.findByDecoTime", query = "SELECT d FROM Divelog d WHERE d.decoTime = :decoTime"),
    @NamedQuery(name = "Divelog.findByTemperature", query = "SELECT d FROM Divelog d WHERE d.temperature = :temperature"),
    @NamedQuery(name = "Divelog.findByGasConsumption", query = "SELECT d FROM Divelog d WHERE d.gasConsumption = :gasConsumption"),
    @NamedQuery(name = "Divelog.findByBuddyName", query = "SELECT d FROM Divelog d WHERE d.buddyName = :buddyName"),
    @NamedQuery(name = "Divelog.findByDivingCenter", query = "SELECT d FROM Divelog d WHERE d.divingCenter = :divingCenter"),
    @NamedQuery(name = "Divelog.findByLocation", query = "SELECT d FROM Divelog d WHERE d.location = :location"),
    @NamedQuery(name = "Divelog.findByCity", query = "SELECT d FROM Divelog d WHERE d.city = :city"),
    @NamedQuery(name = "Divelog.findByCountry", query = "SELECT d FROM Divelog d WHERE d.country = :country"),
    @NamedQuery(name = "Divelog.findByNotes", query = "SELECT d FROM Divelog d WHERE d.notes = :notes")})
public class Divelog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_divelog")
    private Integer idDivelog;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_dive")
    private int numDive;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private int duration;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_depth")
    private double maxDepth;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "avg_depth")
    private Double avgDepth;
    
    @Column(name = "deco_time")
    private Integer decoTime;
    
    @Column(name = "temperature")
    private Double temperature;
    
    @Column(name = "gas_consumption")
    private Double gasConsumption;
    
    @Size(max = 35)
    @Column(name = "buddy_name")
    private String buddyName;
    
    @Size(max = 45)
    @Column(name = "diving_center")
    private String divingCenter;
    
    @Size(max = 35)
    @Column(name = "location")
    private String location;
    
    @Size(max = 30)
    @Column(name = "city")
    private String city;
    
    @Size(max = 25)
    @Column(name = "country")
    private String country;
    
    @Size(max = 150)
    @Column(name = "notes")
    private String notes;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "photo1")
    private String photo1;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "photo2")
    private String photo2;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "photo3")
    private String photo3;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "photo4")
    private String photo4;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divingId")
    private Collection<BuddiesDiving> buddiesDivingCollection;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    @JsonbTransient
    private User userId;

    /*
    * Limitar la recursividad en relaciones ************************************
    */

    @Column(name = "user_id", updatable = false, insertable = false)
    private int idUser;

    public int getIdUser() {
        return idUser;
    }
    
    /*
    ****************************************************************************
    */
    
    public Divelog() {
    }

    public Divelog(Integer idDivelog) {
        this.idDivelog = idDivelog;
    }

    public Divelog(Integer idDivelog, int numDive, Date startDateTime, int duration, double maxDepth) {
        this.idDivelog = idDivelog;
        this.numDive = numDive;
        this.startDateTime = startDateTime;
        this.duration = duration;
        this.maxDepth = maxDepth;
    }

    public Integer getIdDivelog() {
        return idDivelog;
    }

    public void setIdDivelog(Integer idDivelog) {
        this.idDivelog = idDivelog;
    }

    public int getNumDive() {
        return numDive;
    }

    public void setNumDive(int numDive) {
        this.numDive = numDive;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(double maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Double getAvgDepth() {
        return avgDepth;
    }

    public void setAvgDepth(Double avgDepth) {
        this.avgDepth = avgDepth;
    }

    public Integer getDecoTime() {
        return decoTime;
    }

    public void setDecoTime(Integer decoTime) {
        this.decoTime = decoTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getGasConsumption() {
        return gasConsumption;
    }

    public void setGasConsumption(Double gasConsumption) {
        this.gasConsumption = gasConsumption;
    }

    public String getBuddyName() {
        return buddyName;
    }

    public void setBuddyName(String buddyName) {
        this.buddyName = buddyName;
    }

    public String getDivingCenter() {
        return divingCenter;
    }

    public void setDivingCenter(String divingCenter) {
        this.divingCenter = divingCenter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    @XmlTransient
    public Collection<BuddiesDiving> getBuddiesDivingCollection() {
        return buddiesDivingCollection;
    }

    public void setBuddiesDivingCollection(Collection<BuddiesDiving> buddiesDivingCollection) {
        this.buddiesDivingCollection = buddiesDivingCollection;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDivelog != null ? idDivelog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Divelog)) {
            return false;
        }
        Divelog other = (Divelog) object;
        if ((this.idDivelog == null && other.idDivelog != null) || (this.idDivelog != null && !this.idDivelog.equals(other.idDivelog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Divelog[ idDivelog=" + idDivelog + " ]";
    }

}
