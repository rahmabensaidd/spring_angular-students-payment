package tn.rahma.newprojectrahmabensaid4se4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import tn.rahma.newprojectrahmabensaid4se4.entities.Student;
import tn.rahma.newprojectrahmabensaid4se4.entities.Payement;
import tn.rahma.newprojectrahmabensaid4se4.entities.PayementStatus;
import tn.rahma.newprojectrahmabensaid4se4.entities.PayementType;
import tn.rahma.newprojectrahmabensaid4se4.repository.PayementRepo;
import tn.rahma.newprojectrahmabensaid4se4.repository.StudentRepo;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class Newprojectrahmabensaid4Se4Application {

	public static void main(String[] args) {
		SpringApplication.run(Newprojectrahmabensaid4Se4Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepo studentRepo, PayementRepo payementRepo) {
		return args -> {
			// Ajouter des étudiants dans la base de données
			studentRepo.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstname("rahma")
					.lastname("bensaid")
					.code("1123")
					.programId("SDIA")
					.build());

			studentRepo.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstname("hazem")
					.lastname("bensaid")
					.code("1223")
					.programId("SDIA")
					.build());

			studentRepo.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstname("najet")
					.lastname("bensaid")
					.code("1323")
					.programId("GL")
					.build());

			studentRepo.save(Student.builder()
					.id(UUID.randomUUID().toString())
					.firstname("sami")
					.lastname("bensaid")
					.code("1423")
					.programId("GL")
					.build());

			// Génération de paiements pour chaque étudiant
			PayementType[] payementTypes = PayementType.values();
			Random random = new Random();

			studentRepo.findAll().forEach(st -> {
				for (int i = 0; i < 10; i++) {
					// Choix aléatoire d'un type de paiement
					PayementType randomType = payementTypes[random.nextInt(payementTypes.length)];

					// Création d'un paiement
					Payement p = Payement.builder()
							.amount(1000 + random.nextInt(20000)) // Génération d'un montant aléatoire
							.type(randomType)
							.status(PayementStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();

					payementRepo.save(p); // Sauvegarde du paiement
				}
			});
		};
	}
}
