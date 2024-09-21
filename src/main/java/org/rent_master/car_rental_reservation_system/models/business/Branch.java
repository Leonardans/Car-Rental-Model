package org.rent_master.car_rental_reservation_system.models.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.car.Car;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Your branch location is required")
    private String address1;

    private String address2;

    @NotNull
    @NotBlank(message = "City is required")
    private String city;

    @NotNull
    @NotBlank(message = "Country is required")
    private String country;

    @JsonIgnore
    @NotNull(message = "Rental is required")
    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Car> cars;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", address1='" + address1 + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' + '}';
    }

}