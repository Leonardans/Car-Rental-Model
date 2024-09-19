package org.rent_master.car_rental_reservation_system.models.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_images")
public class CarPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @JsonIgnore
    private Car car;

    @Column(name = "picture")
    private String picture;

    @Override
    public String toString() {
        return "CarPicture{" +
                "id=" + id +
                ", carId=" + (car != null ? car.getId() : "null") +
                ", picture='" + picture + '\'' +
                '}';
    }

}
