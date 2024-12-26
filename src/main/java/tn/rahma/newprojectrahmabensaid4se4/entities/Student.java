package tn.rahma.newprojectrahmabensaid4se4.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@ToString
public class Student {
   @Id
    private String id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String code ;
    private String programId;
   private String photo;
}
