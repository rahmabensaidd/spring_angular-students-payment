package tn.rahma.newprojectrahmabensaid4se4.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Payement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    private PayementType  type;
    private PayementStatus status;
     private String file;
  @ManyToOne
  private Student student;

}
