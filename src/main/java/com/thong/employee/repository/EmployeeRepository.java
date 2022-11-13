package com.thong.employee.repository;

import com.thong.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query("SELECT DISTINCT managerId FROM Employee e WHERE managerId NOT IN (SELECT id FROM Employee e2)")
    List<String> findRootManagerIds();

}
