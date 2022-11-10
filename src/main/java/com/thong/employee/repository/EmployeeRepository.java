package com.thong.employee.repository;

import com.thong.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("SELECT managerId FROM Employee e WHERE managerId NOT IN (SELECT id FROM Employee e2)")
    List<String> findRootManagerIds();

}
