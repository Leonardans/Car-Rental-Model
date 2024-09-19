package org.rent_master.car_rental_reservation_system.repositories.business;

import org.rent_master.car_rental_reservation_system.models.business.Employee;
import org.rent_master.car_rental_reservation_system.models.business.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Check Manager Exist
    boolean existsByBranchIdAndPosition(Long branchId, Position position);

    // Check Combo firstname + lastname + position Existing
    boolean existsByFirstnameAndLastnameAndPosition(String firstName, String lastName, Position position);

    // Check all branch employees
    List<Employee> findAllByBranchId(Long branchId);

}
