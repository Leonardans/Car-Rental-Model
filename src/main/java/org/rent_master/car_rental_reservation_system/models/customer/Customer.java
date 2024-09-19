package org.rent_master.car_rental_reservation_system.models.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.user.User;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer extends User {

    @NotBlank(message = " Blank is not allowed")
    private String firstName;

    @NotBlank(message = " Blank is not allowed")
    private String lastName;

    @NotBlank(message = " Blank is not allowed")
    private String address1;

    private String address2;

    @NotBlank(message = " Blank is not allowed")
    private String city;

    @NotBlank(message = " Blank is not allowed")
    private String country;

    @NotBlank
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "customer_licences",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "licence_id")
    )
    private List<Licence> licences;

}
