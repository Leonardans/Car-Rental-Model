package org.rent_master.car_rental_reservation_system.models.customer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "customer_licences")
public class CustomerLicence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "licence_id", nullable = false)
    private Licence licence;

}
