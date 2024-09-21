package org.rent_master.car_rental_reservation_system.models.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.car.Car;
import org.rent_master.car_rental_reservation_system.models.user.User;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer extends User {

    @NotNull
    @NotBlank(message = " Blank is not allowed")
    private String firstName;

    @NotNull
    @NotBlank(message = " Blank is not allowed")
    private String lastName;

    @NotNull
    @NotBlank(message = " Blank is not allowed")
    private String address1;

    private String address2;

    @NotNull
    @NotBlank(message = " Blank is not allowed")
    private String city;

    @NotNull
    @NotBlank(message = " Blank is not allowed")
    private String country;


    @Transient
    private List<Licence> customerLicences;

}
