package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_user;

    @Column
    private String password;

    @Column
    private String nationality;

    @Column(unique = true)
    private String email;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column(nullable = false)
    @Basic
    @Temporal(TemporalType.DATE)
    private Date birthdate;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private Set<Authority> authority;


    public Long getId() {
        return id_user;
    }

    public void setId(Long id) {
        this.id_user = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(Set<Authority> authority) {
        this.authority = authority;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id_user == null) ? 0 : id_user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id_user == null) {
            if (other.id_user != null)
                return false;
        } else if (!id_user.equals(other.id_user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id_user + ", username=" + ", password=" + password + "]";
    }
}