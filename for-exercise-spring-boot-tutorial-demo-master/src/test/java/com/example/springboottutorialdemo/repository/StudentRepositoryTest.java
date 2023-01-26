package com.example.springboottutorialdemo.repository;

import com.example.springboottutorialdemo.entity.StudentEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Test that studentRepository will retrieve a student entity given a student id")
    public void findById() {
        //given: student_id
        int student_id = 1;
        StudentEntity expectedStudentEntity = new StudentEntity(1, "Test Name", 1, "Test Address");
        testEntityManager.persist(expectedStudentEntity);
        //when: studentRepository.findById is executed
        StudentEntity result = studentRepository.findById(student_id).get();
        //then: studentRepository.findById will return a studentEntity that is the same with expectedStudentEntity
        assertEquals(result, expectedStudentEntity);
    }

    //Added Test

    @Test
    @DisplayName("This test that studentRepository will retrieve a student entity")
    public void testFindByName(){

        //Given that there are multiple student entities with same values in name="Test Name" column/attribute
        StudentEntity student1 = new StudentEntity(1,"Jhunel",1,"Unisan, Quezon");
        StudentEntity student2 = new StudentEntity(2,"Jhunel",2,"Unisan, Quezon");
        testEntityManager.persist(student1);
        testEntityManager.persist(student2);
        //When the studentRepository findByNameadd method is executed
        String studentName = "Jhunel";
        List<StudentEntity> result = studentRepository.findByName(studentName);
        //Then the name attribute of all student entities retrieved from database should equal "Test Name"
        assertEquals(result.get(0).getName(),student1.getName());
        assertEquals(result.get(1).getName(),student2.getName());
    }
}