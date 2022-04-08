/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import java.io.Serializable;
import java.util.Collection;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "certification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Certification.findAll", query = "SELECT c FROM Certification c"),
    @NamedQuery(name = "Certification.findByIdCertification", query = "SELECT c FROM Certification c WHERE c.idCertification = :idCertification"),
    @NamedQuery(name = "Certification.findByName", query = "SELECT c FROM Certification c WHERE c.name = :name"),
    @NamedQuery(name = "Certification.findByMaxDeep", query = "SELECT c FROM Certification c WHERE c.maxDeep = :maxDeep"),
    @NamedQuery(name = "Certification.findByDecompresion", query = "SELECT c FROM Certification c WHERE c.decompresion = :decompresion"),
    @NamedQuery(name = "Certification.findByCaveDiving", query = "SELECT c FROM Certification c WHERE c.caveDiving = :caveDiving"),
    @NamedQuery(name = "Certification.findByDpv", query = "SELECT c FROM Certification c WHERE c.dpv = :dpv")})
public class Certification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_certification")
    private Integer idCertification;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "max_deep")
    private int maxDeep;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "decompresion")
    private short decompresion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cave_diving")
    private short caveDiving;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "dpv")
    private short dpv;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "certificationId")
    @JsonbTransient
    private Collection<UserCertification> userCertificationCollection;
    
    @JoinColumn(name = "agency_id", referencedColumnName = "id_agency")
    @ManyToOne(optional = false)
    @JsonbTransient
    private Agency agencyId;

    /*
    * Limitar la recursividad en relaciones ************************************
    */

    // En vez de devolver el Objeto de Agency, devuelve solo la id
    @Column(name = "agency_id", updatable = false, insertable = false)
    private int idAgency;

    public int getIdAgency() {
        return idAgency;
    }

    /*
    ****************************************************************************
    */
    
    public Certification() {
    }

    public Certification(Integer idCertification) {
        this.idCertification = idCertification;
    }

    public Certification(Integer idCertification, String name, int maxDeep, short decompresion, short caveDiving, short dpv) {
        this.idCertification = idCertification;
        this.name = name;
        this.maxDeep = maxDeep;
        this.decompresion = decompresion;
        this.caveDiving = caveDiving;
        this.dpv = dpv;
    }

    public Integer getIdCertification() {
        return idCertification;
    }

    public void setIdCertification(Integer idCertification) {
        this.idCertification = idCertification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxDeep() {
        return maxDeep;
    }

    public void setMaxDeep(int maxDeep) {
        this.maxDeep = maxDeep;
    }

    public short getDecompresion() {
        return decompresion;
    }

    public void setDecompresion(short decompresion) {
        this.decompresion = decompresion;
    }

    public short getCaveDiving() {
        return caveDiving;
    }

    public void setCaveDiving(short caveDiving) {
        this.caveDiving = caveDiving;
    }

    public short getDpv() {
        return dpv;
    }

    public void setDpv(short dpv) {
        this.dpv = dpv;
    }

    @XmlTransient
    public Collection<UserCertification> getUserCertificationCollection() {
        return userCertificationCollection;
    }

    public void setUserCertificationCollection(Collection<UserCertification> userCertificationCollection) {
        this.userCertificationCollection = userCertificationCollection;
    }

    public Agency getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Agency agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCertification != null ? idCertification.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Certification)) {
            return false;
        }
        Certification other = (Certification) object;
        if ((this.idCertification == null && other.idCertification != null) || (this.idCertification != null && !this.idCertification.equals(other.idCertification))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Certification[ idCertification=" + idCertification + " ]";
    }

}
