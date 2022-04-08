/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import java.io.Serializable;
import java.util.Date;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user_certification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserCertification.findAll", query = "SELECT u FROM UserCertification u"),
    @NamedQuery(name = "UserCertification.findByIdUserCertification", query = "SELECT u FROM UserCertification u WHERE u.idUserCertification = :idUserCertification"),
    @NamedQuery(name = "UserCertification.findByDateCertification", query = "SELECT u FROM UserCertification u WHERE u.dateCertification = :dateCertification"),
    @NamedQuery(name = "UserCertification.findByInstructorCode", query = "SELECT u FROM UserCertification u WHERE u.instructorCode = :instructorCode"),
    @NamedQuery(name = "UserCertification.findByInstructorName", query = "SELECT u FROM UserCertification u WHERE u.instructorName = :instructorName"),
    @NamedQuery(name = "UserCertification.findByDivingCenter", query = "SELECT u FROM UserCertification u WHERE u.divingCenter = :divingCenter"),
    @NamedQuery(name = "UserCertification.findByLocation", query = "SELECT u FROM UserCertification u WHERE u.location = :location"),
    @NamedQuery(name = "UserCertification.findByCounty", query = "SELECT u FROM UserCertification u WHERE u.county = :county")})
public class UserCertification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user_certification")
    private Integer idUserCertification;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_certification")
    @Temporal(TemporalType.DATE)
    private Date dateCertification;
    
    @Size(max = 15)
    @Column(name = "instructor_code")
    private String instructorCode;
    
    @Size(max = 35)
    @Column(name = "instructor_name")
    private String instructorName;
    
    @Size(max = 35)
    @Column(name = "diving_center")
    private String divingCenter;
    
    @Size(max = 25)
    @Column(name = "location")
    private String location;
    
    @Size(max = 25)
    @Column(name = "county")
    private String county;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "frontal_photo")
    private String frontalPhoto;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "back_photo")
    private String backPhoto;
    
    @JoinColumn(name = "certification_id", referencedColumnName = "id_certification")
    @ManyToOne(optional = false)
    private Certification certificationId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    @JsonbTransient
    private User userId;
    
    /*
    * Limitar la recursividad en relaciones ************************************
    */

    // En vez de devolver el Objeto de Agencia, devuelve solo la id
    @Column(name = "user_id", updatable = false, insertable = false)
    private int idUser;

    public int getIdUser() {
        return idUser;
    }

    /*
    ****************************************************************************
    */

    public UserCertification() {
    }

    public UserCertification(Integer idUserCertification) {
        this.idUserCertification = idUserCertification;
    }

    public UserCertification(Integer idUserCertification, Date dateCertification) {
        this.idUserCertification = idUserCertification;
        this.dateCertification = dateCertification;
    }

    public Integer getIdUserCertification() {
        return idUserCertification;
    }

    public void setIdUserCertification(Integer idUserCertification) {
        this.idUserCertification = idUserCertification;
    }

    public Date getDateCertification() {
        return dateCertification;
    }

    public void setDateCertification(Date dateCertification) {
        this.dateCertification = dateCertification;
    }

    public String getInstructorCode() {
        return instructorCode;
    }

    public void setInstructorCode(String instructorCode) {
        this.instructorCode = instructorCode;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getFrontalPhoto() {
        return frontalPhoto;
    }

    public void setFrontalPhoto(String frontalPhoto) {
        this.frontalPhoto = frontalPhoto;
    }

    public String getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }

    public Certification getCertificationId() {
        return certificationId;
    }

    public void setCertificationId(Certification certificationId) {
        this.certificationId = certificationId;
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
        hash += (idUserCertification != null ? idUserCertification.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserCertification)) {
            return false;
        }
        UserCertification other = (UserCertification) object;
        if ((this.idUserCertification == null && other.idUserCertification != null) || (this.idUserCertification != null && !this.idUserCertification.equals(other.idUserCertification))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.UserCertification[ idUserCertification=" + idUserCertification + " ]";
    }

}
