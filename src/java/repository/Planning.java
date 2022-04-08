/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "planning")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Planning.findAll", query = "SELECT p FROM Planning p"),
    @NamedQuery(name = "Planning.findByIdPlanning", query = "SELECT p FROM Planning p WHERE p.idPlanning = :idPlanning"),
    @NamedQuery(name = "Planning.findByDateTime", query = "SELECT p FROM Planning p WHERE p.dateTime = :dateTime"),
    @NamedQuery(name = "Planning.findByLocation", query = "SELECT p FROM Planning p WHERE p.location = :location"),
    @NamedQuery(name = "Planning.findByDiveCenter", query = "SELECT p FROM Planning p WHERE p.diveCenter = :diveCenter"),
    @NamedQuery(name = "Planning.findByCity", query = "SELECT p FROM Planning p WHERE p.city = :city"),
    @NamedQuery(name = "Planning.findByCountry", query = "SELECT p FROM Planning p WHERE p.country = :country")})
public class Planning implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_planning")
    private Integer idPlanning;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    
    @Size(max = 45)
    @Column(name = "location")
    private String location;
    
    @Size(max = 45)
    @Column(name = "dive_center")
    private String diveCenter;
    
    @Size(max = 25)
    @Column(name = "city")
    private String city;
    
    @Size(max = 25)
    @Column(name = "country")
    private String country;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User userId;

    public Planning() {
    }

    public Planning(Integer idPlanning) {
        this.idPlanning = idPlanning;
    }

    public Planning(Integer idPlanning, Date dateTime) {
        this.idPlanning = idPlanning;
        this.dateTime = dateTime;
    }

    public Integer getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Integer idPlanning) {
        this.idPlanning = idPlanning;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDiveCenter() {
        return diveCenter;
    }

    public void setDiveCenter(String diveCenter) {
        this.diveCenter = diveCenter;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanning != null ? idPlanning.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planning)) {
            return false;
        }
        Planning other = (Planning) object;
        if ((this.idPlanning == null && other.idPlanning != null) || (this.idPlanning != null && !this.idPlanning.equals(other.idPlanning))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Planning[ idPlanning=" + idPlanning + " ]";
    }

}
