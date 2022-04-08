/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package repository;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbTransient;
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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "buddies_diving")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BuddiesDiving.findAll", query = "SELECT b FROM BuddiesDiving b"),
    @NamedQuery(name = "BuddiesDiving.findByIdRelationBuddy", query = "SELECT b FROM BuddiesDiving b WHERE b.idRelationBuddy = :idRelationBuddy")})
public class BuddiesDiving implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_relation_buddy")
    private Integer idRelationBuddy;
    
    @JoinColumn(name = "diving_id", referencedColumnName = "id_divelog")
    @ManyToOne(optional = false)
    @JsonbTransient
    private Divelog divingId;
    
    @JoinColumn(name = "buddy_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    @JsonbTransient
    private User buddyId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    @JsonbTransient
    private User userId;

    /*
    * Limitar la recursividad en relaciones ************************************
    */

    @Column(name = "buddy_id", updatable = false, insertable = false)
    private int idBuddy;

    public int getIdBuddy() {
        return idBuddy;
    }
    
    /*
    ****************************************************************************
    */
    
    public BuddiesDiving() {
    }

    public BuddiesDiving(Integer idRelationBuddy) {
        this.idRelationBuddy = idRelationBuddy;
    }

    public Integer getIdRelationBuddy() {
        return idRelationBuddy;
    }

    public void setIdRelationBuddy(Integer idRelationBuddy) {
        this.idRelationBuddy = idRelationBuddy;
    }

    public Divelog getDivingId() {
        return divingId;
    }

    public void setDivingId(Divelog divingId) {
        this.divingId = divingId;
    }

    public User getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(User buddyId) {
        this.buddyId = buddyId;
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
        hash += (idRelationBuddy != null ? idRelationBuddy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BuddiesDiving)) {
            return false;
        }
        BuddiesDiving other = (BuddiesDiving) object;
        if ((this.idRelationBuddy == null && other.idRelationBuddy != null) || (this.idRelationBuddy != null && !this.idRelationBuddy.equals(other.idRelationBuddy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.BuddiesDiving[ idRelationBuddy=" + idRelationBuddy + " ]";
    }

}
