package com.example.students.controller;

import com.example.students.exception.BadRequestException;
import com.example.students.exception.NotFoundException;
import com.example.students.model.request.StudentDetailsRequestModel;
import com.example.students.model.response.StudentDetailsResponseModel;
import com.example.students.service.StudentService;
import com.example.students.shared.StudentDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    @Autowired
    StudentService studentService;


    @GetMapping
    public List<StudentDetailsResponseModel> getAllStudents(){
        List<StudentDto> studentDtos = studentService.getAllStudents();
        ArrayList<StudentDetailsResponseModel> responseList = new ArrayList<>();
        for (StudentDto studentDto : studentDtos){
            StudentDetailsResponseModel responseModel= new StudentDetailsResponseModel();
            BeanUtils.copyProperties(studentDto, responseModel);
            responseList.add(responseModel);

        }
        //return productDtos.stream().forEach((productDto) -> BeanUtils.copyProperties(productDto,productDtoOut));
       return responseList;

    }


    @GetMapping(value="/{pId}")
    public ResponseEntity<StudentDetailsResponseModel> getStudent(@PathVariable String pId){
        StudentDetailsResponseModel responseModel = new StudentDetailsResponseModel();
        Optional<StudentDto> optionalStudentDto = studentService.getByStudentId(pId);
        if(optionalStudentDto.isPresent()) {
            StudentDto studentDto = optionalStudentDto.get();
            BeanUtils.copyProperties(studentDto, responseModel);
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
            //return responseModel;
        }
        throw new NotFoundException("***No data with this id***"+ pId);
    }

    @PutMapping("/{pId}")
    public StudentDetailsResponseModel updateStudent(@PathVariable String pId, @RequestBody StudentDetailsRequestModel studentData){
        //Copy Json in to DTO
        StudentDto studentDtoIn = new StudentDto();
        BeanUtils.copyProperties(studentData, studentDtoIn);

        //Pass DTO to Service Layer
        Optional<StudentDto> studentDtoOut = studentService.updateStudent(pId, studentDtoIn);
        if(studentDtoOut.isEmpty()){
            throw new NotFoundException("****No Id Found****");
        }

        StudentDto studentDto = studentDtoOut.get();
        StudentDetailsResponseModel responseModel = new StudentDetailsResponseModel();
        BeanUtils.copyProperties(studentDto, responseModel);
        return responseModel;

    }


    @DeleteMapping("/{pId}")
    public String deleteStudent(@PathVariable String pId){
        StudentDetailsResponseModel responseModel= new StudentDetailsResponseModel();
        boolean deleted = studentService.deleteStudent(pId);
        if(deleted){
            return"";
        }
        throw new BadRequestException("***No product with this id***"+ pId);
    }





    @PostMapping
    public ResponseEntity<StudentDetailsResponseModel> addStudent(@RequestBody StudentDetailsRequestModel studentDetails){
        //Copy Json in to DTO
        StudentDto studentDtoIn = new StudentDto();
        BeanUtils.copyProperties(studentDetails, studentDtoIn);

        //Pass DTO to Service Layer
        StudentDto studentDtoOut = studentService.addStudent(studentDtoIn);

        //Copy DTO from Service Layer to response
        StudentDetailsResponseModel response = new StudentDetailsResponseModel();
        BeanUtils.copyProperties(studentDtoOut, response);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }



}
