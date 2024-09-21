package org.rent_master.car_rental_reservation_system.models.customer;

import jakarta.persistence.*;
import lombok.Data;
import org.rent_master.car_rental_reservation_system.models.car.Car;

@Entity
@Data
@Table(name = "garage_cars")
public class GarageCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

}
