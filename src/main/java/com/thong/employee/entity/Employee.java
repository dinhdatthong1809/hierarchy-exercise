package com.thong.employee.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee implements Serializable {

    @Id
    private String id;

    @Column(name = "managerId")
    private String managerId;

    public Employee() {

    }

    public Employee(String id, String managerId) {
        this.id = id;
        this.managerId = managerId;
    }

}
