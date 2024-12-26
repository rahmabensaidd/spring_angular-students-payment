
package tn.rahma.newprojectrahmabensaid4se4.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.rahma.newprojectrahmabensaid4se4.entities.Student;


import java.util.List;

public interface StudentRepo extends JpaRepository<Student,String> {
    Student findByCode(String code);
    List<Student>findByProgramId(String programId);

}
