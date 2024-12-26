package tn.rahma.newprojectrahmabensaid4se4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.rahma.newprojectrahmabensaid4se4.entities.Payement;
import tn.rahma.newprojectrahmabensaid4se4.entities.PayementStatus;
import tn.rahma.newprojectrahmabensaid4se4.entities.PayementType;


import java.util.List;

public interface PayementRepo extends JpaRepository<Payement,Long> {
    List<Payement> findByStudent_Code(String stcode);
    List<Payement>findByStatus(PayementStatus status);
    List<Payement>findByType(PayementType type);


}
