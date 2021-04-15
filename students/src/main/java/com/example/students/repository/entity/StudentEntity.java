package com.example.students.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name="students")
public class StudentEntity implements Serializable{

    @Id
    @GeneratedValue
    private long id;

    @Column (length = 50, nullable= false)
    private String studentId;

    @Column(length = 50,  nullable= false)
    private String name;

    @Column(length = 50,  nullable= false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private boolean present;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }


}
