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
import javax.persistence.Lob;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByIdUser", query = "SELECT u FROM User u WHERE u.idUser = :idUser"),
    @NamedQuery(name = "User.findByNickname", query = "SELECT u FROM User u WHERE u.nickname = :nickname"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByBirthDate", query = "SELECT u FROM User u WHERE u.birthDate = :birthDate"),
    @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender"),
    @NamedQuery(name = "User.findByPasswordHash", query = "SELECT u FROM User u WHERE u.passwordHash = :passwordHash"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByRegisterDate", query = "SELECT u FROM User u WHERE u.registerDate = :registerDate"),
    @NamedQuery(name = "User.findByPublicProfile", query = "SELECT u FROM User u WHERE u.publicProfile = :publicProfile")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    private Integer idUser;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nickname")
    private String nickname;
    
    @Size(max = 25)
    @Column(name = "first_name")
    private String firstName;
    
    @Size(max = 25)
    @Column(name = "last_name")
    private String lastName;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "photo")
    private String photo;
    
    @Column(name = "birth_date")
    private Integer birthDate;
    
    @Size(max = 15)
    @Column(name = "gender")
    private String gender;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "password_hash")
    private String passwordHash;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "register_date")
    @Temporal(TemporalType.DATE)
    private Date registerDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "public_profile")
    private short publicProfile;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buddyId")
    @JsonbTransient
    private Collection<BuddiesDiving> buddiesDivingCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonbTransient
    private Collection<BuddiesDiving> buddiesDivingCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonbTransient
    private Collection<Planning> planningCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "followerUserId")
    @JsonbTransient
    private Collection<UserSubscription> userSubscriptionCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "liderUserId")
    @JsonbTransient
    private Collection<UserSubscription> userSubscriptionCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonbTransient
    private Collection<UserCertification> userCertificationCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonbTransient
    private Collection<Equipment> equipmentCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonbTransient
    private Collection<Divelog> divelogCollection;

    public User() {
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(Integer idUser, String nickname, String passwordHash, String email, Date registerDate, short publicProfile) {
        this.idUser = idUser;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registerDate = registerDate;
        this.publicProfile = publicProfile;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public short getPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(short publicProfile) {
        this.publicProfile = publicProfile;
    }

    @XmlTransient
    public Collection<BuddiesDiving> getBuddiesDivingCollection() {
        return buddiesDivingCollection;
    }

    public void setBuddiesDivingCollection(Collection<BuddiesDiving> buddiesDivingCollection) {
        this.buddiesDivingCollection = buddiesDivingCollection;
    }

    @XmlTransient
    public Collection<BuddiesDiving> getBuddiesDivingCollection1() {
        return buddiesDivingCollection1;
    }

    public void setBuddiesDivingCollection1(Collection<BuddiesDiving> buddiesDivingCollection1) {
        this.buddiesDivingCollection1 = buddiesDivingCollection1;
    }

    @XmlTransient
    public Collection<Planning> getPlanningCollection() {
        return planningCollection;
    }

    public void setPlanningCollection(Collection<Planning> planningCollection) {
        this.planningCollection = planningCollection;
    }

    @XmlTransient
    public Collection<UserSubscription> getUserSubscriptionCollection() {
        return userSubscriptionCollection;
    }

    public void setUserSubscriptionCollection(Collection<UserSubscription> userSubscriptionCollection) {
        this.userSubscriptionCollection = userSubscriptionCollection;
    }

    @XmlTransient
    public Collection<UserSubscription> getUserSubscriptionCollection1() {
        return userSubscriptionCollection1;
    }

    public void setUserSubscriptionCollection1(Collection<UserSubscription> userSubscriptionCollection1) {
        this.userSubscriptionCollection1 = userSubscriptionCollection1;
    }

    @XmlTransient
    public Collection<UserCertification> getUserCertificationCollection() {
        return userCertificationCollection;
    }

    public void setUserCertificationCollection(Collection<UserCertification> userCertificationCollection) {
        this.userCertificationCollection = userCertificationCollection;
    }

    @XmlTransient
    public Collection<Equipment> getEquipmentCollection() {
        return equipmentCollection;
    }

    public void setEquipmentCollection(Collection<Equipment> equipmentCollection) {
        this.equipmentCollection = equipmentCollection;
    }

    @XmlTransient
    public Collection<Divelog> getDivelogCollection() {
        return divelogCollection;
    }

    public void setDivelogCollection(Collection<Divelog> divelogCollection) {
        this.divelogCollection = divelogCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.User[ idUser=" + idUser + " ]";
    }

}
