package com.gift.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user", schema = "gift")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "provider_user_id")
    private Integer providerUserId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "picture_url")
    private String picture;

    @Column(name = "provider")
    private String provider;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", schema = "gift", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    public Users(Integer id, String firstName, String lastName, String provider, String picture, Integer providerUserId) {
        this.id = Long.valueOf(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.provider = provider;
        this.picture = picture;
        this.providerUserId = providerUserId;
    }

    public String getName () {
        return firstName + " " + lastName;
    }
}
