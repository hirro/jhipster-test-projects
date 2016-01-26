package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TestDtoPagination entity.
 */
public class TestDtoPaginationDTO implements Serializable {

    private Long id;

    private String firstName;


    private String lastName;


    private String country;


    private String email;


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

        TestDtoPaginationDTO testDtoPaginationDTO = (TestDtoPaginationDTO) o;

        if ( ! Objects.equals(id, testDtoPaginationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TestDtoPaginationDTO{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", country='" + country + "'" +
            ", email='" + email + "'" +
            ", ipAddress='" + ipAddress + "'" +
            '}';
    }
}
