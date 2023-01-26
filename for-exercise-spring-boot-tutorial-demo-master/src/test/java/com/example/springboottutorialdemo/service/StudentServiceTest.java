package com.example.springboottutorialdemo.service;

import com.example.springboottutorialdemo.entity.StudentEntity;
import com.example.springboottutorialdemo.exception.StudentNotFoundException;
import com.example.springboottutorialdemo.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    @DisplayName("This tests that if student id is existing, getStudentById will return the student entity")
    public void testGetStudentById_Success() {
        //given: student_id is existing
        int existing_student_id = 1;
        StudentEntity studentEntity = new StudentEntity(1, "Test Name", 1, "Test Address");
        given(studentRepository.findById(existing_student_id)).willReturn(Optional.of(studentEntity));
        //when: studentService.getStudentById is executed
        StudentEntity studentServicesResult = studentService.getStudentById(existing_student_id);
        //then: return of studentService.getStudentById should be equal to return of studentRepository.findById
        assertEquals(studentServicesResult, studentEntity);
    }

    @Test
    @DisplayName("This tests that if student id is non-existing, getStudentById will throw StudentNotFoundException")
    public void testGetStudentById_Fail() {
        //given: student_id is non-existing
        int non_existing_student_id = 1;
        given(studentRepository.findById(non_existing_student_id)).willThrow(new StudentNotFoundException("Student with id : " + non_existing_student_id + " doesn't exist."));
        //when: studentService.getStudentById is executed
        StudentNotFoundException result = assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(non_existing_student_id);
        });
        //then: studentService.getStudentById will throw a StudentNotFoundException with message "Student with id : <non_existing student_id> doesn't exist."
        assertEquals("Student with id : 1 doesn't exist.", result.getMessage());
    }


    ///The code below are the added test cases in accordance with the instruction.
    @Test
    @DisplayName("The test if the a new student is added, addStudent will saved the entity to the studentRepository")

    public void testAddStudent() {
        //Given that there is a new student entity
        StudentEntity student = new StudentEntity(1, "Jhunel", 4, "Unisan,Quezon");
        given(studentRepository.save(student)).willReturn(student);
        //When the studentService add student method is executed
        StudentEntity resultStudent = studentService.addStudent(student);
        //Then it should return the new student entity saved in the studentRepository
        assertEquals(resultStudent, student);

    }

    @Test
    @DisplayName("This tests that if student id is existing,the getStudentById will return null")
    public void testDeleteStudentById_Success() {
        int studentId = 1;
        //Given that there is an existing student id
        StudentEntity student = new StudentEntity(1, "Jhunel", 4, "Unisan, Quezon");
        given(studentRepository.findById(studentId)).willReturn(Optional.of(student));

        //When the studentService deleteStudentById method is executed
        StudentEntity resultStudent = studentService.deleteStudentById(studentId);

        //Then it will return null
        assertEquals(resultStudent, null);
        verify(studentRepository, atLeastOnce()).delete(student);
    }

    @Test
    @DisplayName("This tests that if student id is existing,the getStudentById will return StudentNotFoundException exception with message Student with id : <non-existing_id> doesn't exist.")
    public void testDeleteStudentById_Fail() {
        //Given that there is an existing student id
        int studentId = 1;
        given(studentRepository.findById(studentId)).willThrow(new StudentNotFoundException("Student with id : " + studentId + " doesn't exist."));
        //When the studentService deleteStudentById method is executed
        StudentNotFoundException result = assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(studentId);
        });
        //Then it will return throw StudentNotFoundException exception
        assertEquals("Student with id : 1 doesn't exist.", result.getMessage());
    }


}