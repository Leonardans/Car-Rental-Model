package org.rent_master.car_rental_reservation_system.models.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.user.User;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee extends User {

    @NotNull
    @NotBlank(message = "Your firstname   is required")
    private String firstname;

    @NotNull
    @NotBlank(message = "Your lastname  is required")
    private String lastname;

    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Position position;

    @JsonIgnore
    @NotBlank(message = "Branch is required")
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

}
