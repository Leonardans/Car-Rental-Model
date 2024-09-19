package org.rent_master.car_rental_reservation_system.models.customer;

import lombok.Getter;

@Getter
public enum Categories {
    AM_MOPEED(1),
    A_MOOTORRATAS(2),
    B_SOIDUAUTO(3),
    C_VEOAUTO(4),
    D_AUTOBUSS(5);

    private final int id;

    Categories(int id) {
        this.id = id;
    }

    public static Categories fromString(String category) {
        return Categories.valueOf(category.toUpperCase());
    }
}