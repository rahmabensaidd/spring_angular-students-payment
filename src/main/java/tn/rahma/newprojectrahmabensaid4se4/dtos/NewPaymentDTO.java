package tn.rahma.newprojectrahmabensaid4se4.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.rahma.newprojectrahmabensaid4se4.entities.PayementType;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPaymentDTO {
  private  LocalDate date ;
   private  double amount ;
   private PayementType type ;
   private  String studentcode;
}
