package com.example.students.service.impl;

import com.example.students.exception.BadRequestException;
import com.example.students.repository.StudentRepository;
import com.example.students.repository.entity.StudentEntity;
import com.example.students.service.StudentService;
import com.example.students.shared.StudentDto;
import com.example.students.shared.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    //@Autowired
    private final StudentRepository studentRepository;
    private final Util util;

    public StudentServiceImpl(StudentRepository studentRepository, Util util) {
        this.studentRepository = studentRepository;
        this.util = util;
    }

    public StudentDto addStudent(StudentDto studentDetailsIn){
      if(studentDetailsIn.getName().equals("")||
              studentDetailsIn.getLastName().equals("")||
              studentDetailsIn.getAge()<18

      ){
          throw new BadRequestException("You did not provide required data");
      }


        StudentEntity studentEntity = new StudentEntity();
        BeanUtils.copyProperties(studentDetailsIn, studentEntity);
        String productId = util.generateHash(studentDetailsIn.getName());
        studentEntity.setStudentId(productId);
        StudentEntity studentEntityOut = studentRepository.save(studentEntity);
        StudentDto studentDtoOut = new StudentDto();
        BeanUtils.copyProperties(studentEntityOut, studentDtoOut);
        return studentDtoOut;
    }


    public Optional<StudentDto> getByStudentId(String pId) {
        Optional<StudentEntity> studentIdEntity = studentRepository.findByStudentId(pId);
        return studentIdEntity.map(studentEntity ->{
              StudentDto studentDto = new StudentDto();
              BeanUtils.copyProperties(studentEntity, studentDto);
              return studentDto;
        });
    }


    public Optional<StudentDto> updateStudent(String pId, StudentDto studentDtoIn) {
        Optional<StudentEntity> studentIdEntity = studentRepository.findByStudentId(pId);
        if (studentIdEntity.isEmpty())
            return Optional.empty();
        return studentIdEntity.map(studentEntity -> {
            StudentDto response = new StudentDto();
            //return data which has not changed
            studentEntity.setStudentId(studentDtoIn.getStudentId() !=null ? util.generateHash(studentDtoIn.getName().substring(3)) : studentEntity.getStudentId());
            studentEntity.setLastName(studentDtoIn.getLastName() !=null ? studentDtoIn.getLastName(): studentEntity.getLastName());
            studentEntity.setAge(studentDtoIn.getAge() <18 ? studentEntity.getAge() : studentDtoIn.getAge());
            studentEntity.setName(studentDtoIn.getName() !=null ? studentDtoIn.getName(): studentEntity.getName());
            studentEntity.setPresent(studentDtoIn.isPresent());

            StudentEntity updatedStudentEntity = studentRepository.save(studentEntity);
            BeanUtils.copyProperties(updatedStudentEntity,response);
            return response;
        });
    }

    @Transactional
    public boolean deleteStudent(String pId) {
        long removedStudentCount = studentRepository.deleteByStudentId(pId);
        return removedStudentCount>0;
    }


    public List<StudentDto> getAllStudents() {
        Iterable<StudentEntity> studentEntities = studentRepository.findAll();
        ArrayList<StudentDto> studentDtos = new ArrayList<>();
        for(StudentEntity studentEntity : studentEntities){
            StudentDto studentDto = new StudentDto();
            BeanUtils.copyProperties(studentEntity, studentDto);
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }


}
