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

    @Query("""
            SELECT DISTINCT managerId
            FROM Employee e
            WHERE managerId NOT IN (SELECT id FROM Employee e2)
            """)
    List<String> findRootManagerIds();

    @Query(value = """
            WITH RECURSIVE all_managers AS (
                SELECT
                    id,
                    manager_id
                FROM employee
                WHERE id = :employeeId
            UNION
                SELECT
                    e.id,
                    e.manager_id
                FROM all_managers m
                INNER JOIN employee e ON m.manager_id = e.id
            ) SELECT manager_id FROM all_managers
            """,
            nativeQuery = true)
    List<String> findManagersOfEmployee(String employeeId);

}
