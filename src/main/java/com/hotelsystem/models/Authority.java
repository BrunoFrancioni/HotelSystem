package com.hotelsystem.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@NamedQuery(name="Authority.user",query = "SELECT a FROM Authority a WHERE a.authority='USER'")
public class Authority {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id_authority;

    @Column(unique = true)
    private String authority;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}