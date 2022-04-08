
package repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "agency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agency.findAll", query = "SELECT a FROM Agency a"),
    @NamedQuery(name = "Agency.findByIdAgency", query = "SELECT a FROM Agency a WHERE a.idAgency = :idAgency"),
    @NamedQuery(name = "Agency.findByAcronym", query = "SELECT a FROM Agency a WHERE a.acronym = :acronym"),
    @NamedQuery(name = "Agency.findByFullName", query = "SELECT a FROM Agency a WHERE a.fullName = :fullName"),
    @NamedQuery(name = "Agency.findByWebsiteUrl", query = "SELECT a FROM Agency a WHERE a.websiteUrl = :websiteUrl")})
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_agency")
    private int idAgency;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "full_name")
    private String fullName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "acronym")
    private String acronym;
    
    @Size(max = 50)
    @Column(name = "website_url")
    private String websiteUrl;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencyId")
    @JsonbTransient
    private Collection<Certification> certificationCollection;

    public Agency() {
    }

    public Agency(int idAgency, String fullName, String acronym, String websiteUrl) {
        this.idAgency = idAgency;
        this.fullName = fullName;
        this.acronym = acronym;
        this.websiteUrl = websiteUrl;
    }

    public int getIdAgency() {
        return idAgency;
    }

    public void setIdAgency(int idAgency) {
        this.idAgency = idAgency;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @XmlTransient
    public Collection<Certification> getCertificationCollection() {
        return certificationCollection;
    }

    public void setCertificationCollection(Collection<Certification> certificationCollection) {
        this.certificationCollection = certificationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.idAgency;
        hash = 97 * hash + Objects.hashCode(this.fullName);
        hash = 97 * hash + Objects.hashCode(this.acronym);
        hash = 97 * hash + Objects.hashCode(this.websiteUrl);
        hash = 97 * hash + Objects.hashCode(this.certificationCollection);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Agency other = (Agency) obj;
        if (this.idAgency != other.idAgency) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.acronym, other.acronym)) {
            return false;
        }
        if (!Objects.equals(this.websiteUrl, other.websiteUrl)) {
            return false;
        }
        if (!Objects.equals(this.certificationCollection, other.certificationCollection)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agency{" + "idAgency=" + idAgency + ", fullName=" + fullName + ", acronym=" + acronym + ", websiteUrl=" + websiteUrl + '}';
    }

}
