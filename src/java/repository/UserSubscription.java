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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user_subscription")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserSubscription.findAll", query = "SELECT u FROM UserSubscription u"),
    @NamedQuery(name = "UserSubscription.findByIdSubscription", query = "SELECT u FROM UserSubscription u WHERE u.idSubscription = :idSubscription"),
    @NamedQuery(name = "UserSubscription.findByDateSubscription", query = "SELECT u FROM UserSubscription u WHERE u.dateSubscription = :dateSubscription")})
public class UserSubscription implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_subscription")
    private Integer idSubscription;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_subscription")
    @Temporal(TemporalType.DATE)
    private Date dateSubscription;
    
    @JoinColumn(name = "follower_user_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User followerUserId;
    
    @JoinColumn(name = "lider_user_id", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User liderUserId;

    public UserSubscription() {
    }

    public UserSubscription(Integer idSubscription) {
        this.idSubscription = idSubscription;
    }

    public UserSubscription(Integer idSubscription, Date dateSubscription) {
        this.idSubscription = idSubscription;
        this.dateSubscription = dateSubscription;
    }

    public Integer getIdSubscription() {
        return idSubscription;
    }

    public void setIdSubscription(Integer idSubscription) {
        this.idSubscription = idSubscription;
    }

    public Date getDateSubscription() {
        return dateSubscription;
    }

    public void setDateSubscription(Date dateSubscription) {
        this.dateSubscription = dateSubscription;
    }

    public User getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(User followerUserId) {
        this.followerUserId = followerUserId;
    }

    public User getLiderUserId() {
        return liderUserId;
    }

    public void setLiderUserId(User liderUserId) {
        this.liderUserId = liderUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubscription != null ? idSubscription.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserSubscription)) {
            return false;
        }
        UserSubscription other = (UserSubscription) object;
        if ((this.idSubscription == null && other.idSubscription != null) || (this.idSubscription != null && !this.idSubscription.equals(other.idSubscription))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.UserSubscription[ idSubscription=" + idSubscription + " ]";
    }

}
