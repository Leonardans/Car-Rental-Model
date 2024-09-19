package org.rent_master.car_rental_reservation_system.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validations {

    // Regex for Validation
    private static final String COMPANY_NAME_REGEX = "^(?=.{1,20}$)([a-zA-Z]+(?:\\s[a-zA-Z]+){0,2})\\.?$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
    private static final String FULL_NAME_REGEX = "^(?=.*\\S)([A-Za-zА-Яа-яЁёÀ-ÿ]{2,}\\s+)+[A-Za-zА-Яа-яЁёÀ-ÿ]{2,}$";
    private static final String NAME_REGEX = "^[A-Za-zА-Яа-яЁёÀ-ÿ\\s-]{2,}$";
    private static final String ADDRESS_REGEX = "^[A-Za-z0-9À-ÿ\\s,.-]{5,}$";
    private static final String CITY_REGEX = "^[A-Za-zА-Яа-яЁёÀ-ÿ\\s-]{2,}$";
    private static final String COUNTRY_REGEX = "^[A-Za-zА-Яа-яЁёÀ-ÿ\\s-]{2,}$";


    public static boolean isValidCompanyName(String companyName) {
        return !Pattern.matches(COMPANY_NAME_REGEX, companyName);
    }
    public static boolean isValidUsername(String username) {
        return !Pattern.matches(USERNAME_REGEX, username);
    }
    public static boolean isValidEmail(String email) {
        return !Pattern.matches(EMAIL_REGEX, email);
    }
    public static boolean isValidPassword(String password) {
        return !Pattern.matches(PASSWORD_REGEX, password);
    }
    public static boolean isValidFullName(String fullName) {
        return !Pattern.matches(FULL_NAME_REGEX, fullName);
    }
    public static boolean isValidFirstName(String firstName) {
        return !Pattern.matches(NAME_REGEX, firstName);
    }
    public static boolean isValidLastName(String lastName) {
        return !Pattern.matches(NAME_REGEX, lastName);
    }
    public static boolean isValidAddress(String address) {
        return !Pattern.matches(ADDRESS_REGEX, address);
    }
    public static boolean isValidCity(String city) {
        return !Pattern.matches(CITY_REGEX, city);
    }
    public static boolean isValidCountry(String country) {
        return !Pattern.matches(COUNTRY_REGEX, country);
    }

    // Validate Reservations Dates
    public static void validateReservationDates(String dateFrom, String dateTo) {
        // Check Date
        if (dateFrom == null || dateTo == null) {
            throw new IllegalArgumentException("DateFrom and DateTo must not be null");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        try {
            // Parsing
            LocalDate localDateFrom = LocalDate.parse(dateFrom, formatter);
            LocalDate localDateTo = LocalDate.parse(dateTo, formatter);

            // Past Checking
            if (localDateFrom.isBefore(today) || localDateTo.isBefore(today)) {
                throw new IllegalArgumentException("Dates must not be in the past!");
            }

            // Check that DateTo is after DateFrom
            if (localDateTo.isBefore(localDateFrom)) {
                throw new IllegalArgumentException("DateTo must not be before DateFrom!");
            }

            // Check the minimal rent period
            if (!localDateTo.isAfter(localDateFrom.plusDays(1))) {
                throw new IllegalArgumentException("The rental period must be at least 3 days!");
            }

            // Check Format
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.", e);
        }
    }

}
