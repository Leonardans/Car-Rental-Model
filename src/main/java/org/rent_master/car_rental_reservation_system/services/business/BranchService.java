package org.rent_master.car_rental_reservation_system.services.business;

import jakarta.persistence.EntityNotFoundException;
import org.rent_master.car_rental_reservation_system.DTO.business.BranchCreationDTO;
import org.rent_master.car_rental_reservation_system.DTO.reservation.BranchInfoDTO;
import org.rent_master.car_rental_reservation_system.models.business.Branch;
import org.rent_master.car_rental_reservation_system.models.business.Rental;
import org.rent_master.car_rental_reservation_system.repositories.business.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private final RentalService rentalService;
    private final BranchRepository branchRepository;

    @Value("${logo.resource-url}")
    private String logoResourceUrl;


    @Autowired
    public BranchService(RentalService rentalService, BranchRepository branchRepository) {
        this.rentalService = rentalService;
        this.branchRepository = branchRepository;
    }


    // CREATE : Create New Branch
    public void createBranch(BranchCreationDTO branchCreationDTO, Long rentalId) {
        System.out.println(branchCreationDTO);
        Branch branch = new Branch();

        try {
            Rental rental = rentalService.readRentalById(rentalId);
            branch.setRental(rental);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Rental not found with id: " + rentalId);
        }

        // Open Branch Logic
        branch.setAddress1(branchCreationDTO.getAddress1());
        if (branchCreationDTO.getAddress2() != null) {
            branch.setAddress2(branchCreationDTO.getAddress2());
        }
        branch.setCity(branchCreationDTO.getCity());
        branch.setCountry(branchCreationDTO.getCountry());

        this.branchRepository.save(branch);
    }

    // READ : Find all rental branches
    public List<Branch> readAllBranchesByRentalId(Long rentalId) {
        return this.branchRepository.findByRentalId(rentalId);
    }

    // READ : Rental + Branch Checking
    public Branch readBranchByBranchIdAndRentalId(Long branchId, Long rentalId) {
        return this.branchRepository.findByIdAndRentalId(branchId, rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found!"));
    }

    // READ : Get Branch Info
    public BranchInfoDTO readBranchInfo(Long branchId, Long rentalId) {
        Branch branch;
        try {
            branch = this.readBranchByBranchIdAndRentalId(branchId, rentalId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Branch not found!");
        }

        Rental rental = branch.getRental();
        BranchInfoDTO branchInfoDTO = BranchInfoDTO.fromBranch(branch);

        if (rental != null) {
            branchInfoDTO.setName(rental.getName());
            branchInfoDTO.setLogoUrl(logoResourceUrl + rental.getLogo());
        }

        return branchInfoDTO;
    }

    // DELETE : Delete one Branch
    public void deleteBranch(Long branchId, Long rentalId) {
        Branch branch = new Branch();

        try {
            Rental rental = rentalService.readRentalById(rentalId);
            branch.setRental(rental);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Rental not found with id: " + rentalId);
        }

        this.branchRepository.deleteById(branchId);
    }

}
