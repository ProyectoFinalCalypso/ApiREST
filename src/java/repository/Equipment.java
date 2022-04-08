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
@Table(name = "equipment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment e"),
    @NamedQuery(name = "Equipment.findByIdEquipment", query = "SELECT e FROM Equipment e WHERE e.idEquipment = :idEquipment"),
    @NamedQuery(name = "Equipment.findByName", query = "SELECT e FROM Equipment e WHERE e.name = :name"),
    @NamedQuery(name = "Equipment.findByType", query = "SELECT e FROM Equipment e WHERE e.type = :type"),
    @NamedQuery(name = "Equipment.findByDatePurchase", query = "SELECT e FROM Equipment e WHERE e.datePurchase = :datePurchase"),
    @NamedQuery(name = "Equipment.findByLastCheckDate", query = "SELECT e FROM Equipment e WHERE e.lastCheckDate = :lastCheckDate"),
    @NamedQuery(name = "Equipment.findByCheckRecommendedMonths", query = "SELECT e FROM Equipment e WHERE e.checkRecommendedMonths = :checkRecommendedMonths"),
    @NamedQuery(name = "Equipment.findByCheckRecommendedHours", query = "SELECT e FROM Equipment e WHERE e.checkRecommendedHours = :checkRecommendedHours"),
    @NamedQuery(name = "Equipment.findByCheckRecommendedDives", query = "SELECT e FROM Equipment e WHERE e.checkRecommendedDives = :checkRecommendedDives"),
    @NamedQuery(name = "Equipment.findByUsedDives", query = "SELECT e FROM Equipment e WHERE e.usedDives = :usedDives"),
    @NamedQuery(name = "Equipment.findByUsedDivesAfterCheck", query = "SELECT e FROM Equipment e WHERE e.usedDivesAfterCheck = :usedDivesAfterCheck"),
    @NamedQuery(name = "Equipment.findByUsedHours", query = "SELECT e FROM Equipment e WHERE e.usedHours = :usedHours"),
    @NamedQuery(name = "Equipment.findByUsedHoursAfterCheck", query = "SELECT e FROM Equipment e WHERE e.usedHoursAfterCheck = :usedHoursAfterCheck")})
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equipment")
    private Integer idEquipment;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "type")
    private String type;
    
    @Column(name = "date_purchase")
    @Temporal(TemporalType.DATE)
    private Date datePurchase;
    
    @Column(name = "last_check_date")
    @Temporal(TemporalType.DATE)
    private Date lastCheckDate;
    
    @Column(name = "check_recommended_months")
    private Integer checkRecommendedMonths;
    
    @Column(name = "check_recommended_hours")
    private Integer checkRecommendedHours;
    
    @Column(name = "check_recommended_dives")
    private Integer checkRecommendedDives;
    
    @Column(name = "used_dives")
    private Integer usedDives;
    
    @Column(name = "used_dives_after_check")
    private Integer usedDivesAfterCheck;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "used_hours")
    private Double usedHours;
    
    @Column(name = "used_hours_after_check")
    private Double usedHoursAfterCheck;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User userId;

    public Equipment() {
    }

    public Equipment(Integer idEquipment) {
        this.idEquipment = idEquipment;
    }

    public Equipment(Integer idEquipment, String name, String type) {
        this.idEquipment = idEquipment;
        this.name = name;
        this.type = type;
    }

    public Integer getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(Integer idEquipment) {
        this.idEquipment = idEquipment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Date datePurchase) {
        this.datePurchase = datePurchase;
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public Integer getCheckRecommendedMonths() {
        return checkRecommendedMonths;
    }

    public void setCheckRecommendedMonths(Integer checkRecommendedMonths) {
        this.checkRecommendedMonths = checkRecommendedMonths;
    }

    public Integer getCheckRecommendedHours() {
        return checkRecommendedHours;
    }

    public void setCheckRecommendedHours(Integer checkRecommendedHours) {
        this.checkRecommendedHours = checkRecommendedHours;
    }

    public Integer getCheckRecommendedDives() {
        return checkRecommendedDives;
    }

    public void setCheckRecommendedDives(Integer checkRecommendedDives) {
        this.checkRecommendedDives = checkRecommendedDives;
    }

    public Integer getUsedDives() {
        return usedDives;
    }

    public void setUsedDives(Integer usedDives) {
        this.usedDives = usedDives;
    }

    public Integer getUsedDivesAfterCheck() {
        return usedDivesAfterCheck;
    }

    public void setUsedDivesAfterCheck(Integer usedDivesAfterCheck) {
        this.usedDivesAfterCheck = usedDivesAfterCheck;
    }

    public Double getUsedHours() {
        return usedHours;
    }

    public void setUsedHours(Double usedHours) {
        this.usedHours = usedHours;
    }

    public Double getUsedHoursAfterCheck() {
        return usedHoursAfterCheck;
    }

    public void setUsedHoursAfterCheck(Double usedHoursAfterCheck) {
        this.usedHoursAfterCheck = usedHoursAfterCheck;
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
        hash += (idEquipment != null ? idEquipment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipment)) {
            return false;
        }
        Equipment other = (Equipment) object;
        if ((this.idEquipment == null && other.idEquipment != null) || (this.idEquipment != null && !this.idEquipment.equals(other.idEquipment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Equipment[ idEquipment=" + idEquipment + " ]";
    }

}
