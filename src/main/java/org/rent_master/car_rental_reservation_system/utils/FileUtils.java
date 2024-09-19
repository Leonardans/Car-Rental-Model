package org.rent_master.car_rental_reservation_system.utils;

import org.rent_master.car_rental_reservation_system.models.car.Car;
import org.rent_master.car_rental_reservation_system.models.car.CarPicture;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    // Check file type
    public static String determineContentType(String fileName) {
        if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }

    // Save Image
    public static String addImageToDirectory(MultipartFile image, String dir) {
        try {
            System.out.println("Received file: " + image.getOriginalFilename() + " || 2 : STEP");
            System.out.println("Upload directory: " + dir);

            File directory = new File(dir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new RuntimeException("Failed to create directory: " + dir);
                }
            }

            String fileName = image.getOriginalFilename();
            assert fileName != null;
            File file = new File(directory, fileName);
            image.transferTo(file);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save logo: " + image);
        }
    }

    // Delete Car Pictures
    public static void deleteCarPictures(Car car, String carUploadDir) {
        if (car.getPictures() != null) {
            for (CarPicture carPicture : car.getPictures()) {
                String fileName = carPicture.getPicture();
                File file = new File(carUploadDir, fileName);
                if (file.exists()) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Deleted picture: " + fileName);
                    } else {
                        System.out.println("Failed to delete picture: " + fileName);
                    }
                } else {
                    System.out.println("Picture not found: " + fileName);
                }
            }
        }
    }

}
