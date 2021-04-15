package com.example.students.service;
import com.example.students.shared.StudentDto;

import java.util.List;
import java.util.Optional;


public interface StudentService {

  StudentDto addStudent(StudentDto userDetails);
  Optional<StudentDto> getByStudentId(String pId);
  Optional<StudentDto> updateStudent(String pId, StudentDto studentDtoIn);
  boolean deleteStudent(String pId);
  List<StudentDto> getAllStudents();
}
