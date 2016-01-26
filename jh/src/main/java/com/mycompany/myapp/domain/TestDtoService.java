package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TestDtoService.
 */
@Entity
@Table(name = "test_dto_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TestDtoService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestDtoService testDtoService = (TestDtoService) o;
        if(testDtoService.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, testDtoService.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TestDtoService{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", country='" + country + "'" +
            ", email='" + email + "'" +
            ", ipAddress='" + ipAddress + "'" +
            '}';
    }
}
