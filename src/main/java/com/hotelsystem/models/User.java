package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_user;

    @Column(nullable = false, length = 255, name = "email", unique = true)
    private String email;

    @Column(nullable = false, length = 255, name = "password")
    private String password;

    @Column(nullable = false, length = 100, name = "first_name")
    private String first_name;

    @Column(nullable = false, length = 100, name = "last_name")
    private String last_name;

    @Column(nullable = false, name = "birthdate")
    @Basic
    @Temporal(TemporalType.DATE)
    private java.util.Date birthdate;

    @Column(nullable = false, length = 100, name = "nationality")
    private String nationality;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<Booking>();

    @Version
    private Integer version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authority_user", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private Set<Authority> authority;

    public User() {
    }

    public User(Long id_user, String email, String password, String first_name, String last_name, Date birthdate, String nationality, Integer version) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.version = version;
    }

    public User(String email, String password, Collection<? extends GrantedAuthority> authorities){
        this.email=email;
        this.password=password;
        for(GrantedAuthority a :authorities){
            authority.add((Authority) a);
        }
    }

    /*public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> list = new ArrayList<>();
        for (Authority a : authority){
            list.add(a);
        }
        return list;
    }*/

    public void setAuthorities(Authority a){
        authority.add(a);
    }

    public void setAuthorities(List<Authority> list){
        for(Authority a : list){
            authority.add(a);
        }
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getVersion() {return version;}

    public void setVersion(Integer version) {this.version = version;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id_user.equals(user.id_user) &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                first_name.equals(user.first_name) &&
                last_name.equals(user.last_name) &&
                birthdate.equals(user.birthdate) &&
                nationality.equals(user.nationality) &&
                version.equals(user.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_user, email, password, first_name, last_name, birthdate, nationality, version);
    }

}
