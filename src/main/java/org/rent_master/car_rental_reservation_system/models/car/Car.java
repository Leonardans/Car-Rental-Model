package org.rent_master.car_rental_reservation_system.models.car;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rent_master.car_rental_reservation_system.models.business.Branch;

import java.math.BigDecimal;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel carModel;

    @ManyToOne
    @JoinColumn(name = "body_type_Id")
    private BodyType bodyType;

    @NotNull
    @Min(value = 1980, message = "To work for rent, the car must be a newer model")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private CarColor carColor;

    @NotNull
    private String carStateNumberPlate;

    @Min(value = 1, message = "Mileage can't be less")
    private Integer mileage;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private BigDecimal amount;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CarPicture> pictures;

    @NotBlank(message = "City is required")
    private String city;

    @Transient
    private List<String> imagePaths;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand=" + (brand != null ? brand.getName() : "null") +
                ", carModel=" + (carModel != null ? carModel.getName() : "null") +
                ", bodyType=" + (bodyType != null ? bodyType.getName() : "null") +
                ", year=" + year +
                ", carColor=" + (carColor != null ? carColor.getName() : "null") +
                ", carStateNumberPlate='" + carStateNumberPlate + '\'' +
                ", mileage=" + mileage +
                ", status=" + status +
                ", amount=" + amount +
                ", city='" + city + '\'' + '}';
    }

}
