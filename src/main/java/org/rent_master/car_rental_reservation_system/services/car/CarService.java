package org.rent_master.car_rental_reservation_system.services.car;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.rent_master.car_rental_reservation_system.DTO.car.CarCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.car.CarDTO;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.business.Position;
import org.rent_master.car_rental_reservation_system.models.car.Car;
import org.rent_master.car_rental_reservation_system.models.car.CarPicture;
import org.rent_master.car_rental_reservation_system.models.car.Status;
import org.rent_master.car_rental_reservation_system.repositories.car.CarRepository;
import org.rent_master.car_rental_reservation_system.services.business.BranchService;
import org.rent_master.car_rental_reservation_system.services.business.EmployeeService;
import org.rent_master.car_rental_reservation_system.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CarService {

    private final BranchService branchService;
    private final EmployeeService employeeService;
    private final BrandService brandService;
    private final CarModelService carModelService;
    private final BodyTypeService bodyTypeService;
    private final CarColorService carColorService;
    private final CarRepository carRepository;

    @Value("${car.upload-dir}")
    private String carUploadDir;
    @Value("${car.resource-url}")
    private String carResourceUrl;

    @Autowired
    public CarService(CarRepository carRepository, BranchService branchService, EmployeeService employeeService,
                      CarModelService carModelService, BodyTypeService bodyTypeService,
                      CarColorService carColorService, BrandService brandService) {
        this.carRepository = carRepository;
        this.branchService = branchService;
        this.employeeService = employeeService;
        this.brandService = brandService;
        this.carModelService = carModelService;
        this.bodyTypeService = bodyTypeService;
        this.carColorService = carColorService;
    }


    // CREATE : Create new Car
    @Transactional
    public void createCar(CarCreationDTO carCreationDTO, Long branchId, Long rentalId) {
        System.out.println(carCreationDTO);
        // Check Branch in Rental
        Branch branch;
        try {
            branch = branchService.readBranchByBranchIdAndRentalId(branchId, rentalId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Branch not found!");
        }

        // Check if Manager Present
        if (!employeeService.ifManagerExistsInBranch(branchId, Position.MANAGER)) {
            throw new IllegalArgumentException("A manager must be hired in the branch with ID " + branchId + " " +
                    "before adding a car!");
        }

        // Check by Number Plate Car Existing
        if (carRepository.existsByCarStateNumberPlate(carCreationDTO.getCarStateNumberPlate())) {
            throw new IllegalArgumentException("Car with state number plate " + carCreationDTO.getCarStateNumberPlate()
                    + " is already registered");
        }

        Car car = new Car();
        car.setAmount(carCreationDTO.getAmount());
        car.setCarStateNumberPlate(carCreationDTO.getCarStateNumberPlate());
        car.setMileage(carCreationDTO.getMileage());
        car.setYear(carCreationDTO.getYear());
        car.setBodyType(bodyTypeService.readByName(carCreationDTO.getBodyType()));
        car.setBrand(brandService.readByName(carCreationDTO.getBrand()));
        car.setCarModel(carModelService.readByName(carCreationDTO.getModel()));
        car.setCarColor(carColorService.readByName(carCreationDTO.getColor()));
        car.setCity(branch.getCity());
        car.setBranch(branch);
        car.setStatus(Status.AVAILABLE);

        try {
            List<CarPicture> carPictures = new ArrayList<>();
            for (MultipartFile picture : carCreationDTO.getPictures()) {
                System.out.println(picture + " || 1 : STEP");
                if (picture != null && !picture.isEmpty()) {
                    System.out.println(picture);
                    System.out.println("Is picture empty: " + picture.isEmpty());
                    String fileName = FileUtils.addImageToDirectory(picture, carUploadDir);

                    CarPicture carPicture = new CarPicture();
                    carPicture.setCar(car);
                    carPicture.setPicture(fileName);
                    System.out.println(carPicture + " || 3 : STEP");
                    carPictures.add(carPicture);
                }
            }
            car.setPictures(carPictures);
            System.out.println(carPictures);
            if (car.getPictures() == null || car.getPictures().isEmpty()) {
                throw new IllegalArgumentException("At least one picture is required for the car.");
            }

            // Save Car
            carRepository.save(car);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // READ : Get all Cars with Pictures
    @Transactional
    public List<CarDTO> readAllCars() {
        List<Car> cars = carRepository.findAll();

        return cars.stream()
                .map(car -> {
                    List<String> imagePaths = car.getPictures().stream()
                            .map(carPicture -> carResourceUrl + carPicture.getPicture())
                            .collect(Collectors.toList());
                    Branch branch = car.getBranch();
                    return CarDTO.fromBaseCar(car, imagePaths, branch);
                })
                .collect(Collectors.toList());

    }

    // DELETE : Delete Car by ID
    @Transactional
    public void deleteCar(Long carId, Long branchId, Long rentalId) {
        // Check Branch in Rental
        try {
            branchService.readBranchByBranchIdAndRentalId(branchId, rentalId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Branch not found!");
        }

        // Check Car
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) {
            throw new EntityNotFoundException ("Car with ID " + carId + " does not exist");
        }

        // Check does car belong to concretely branch
        if (!car.getBranch().getId().equals(branchId)) {
            throw new IllegalArgumentException ("Car with ID " + carId + " does not belong to branch with ID " + branchId);
        }

        System.out.println("Deleting car with ID: " + carId);
        FileUtils.deleteCarPictures(car, carUploadDir);
        carRepository.deleteById(carId);
    }

}
