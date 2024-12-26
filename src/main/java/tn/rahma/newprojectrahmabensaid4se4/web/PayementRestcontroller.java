package tn.rahma.newprojectrahmabensaid4se4.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.rahma.newprojectrahmabensaid4se4.dtos.NewPaymentDTO;
import tn.rahma.newprojectrahmabensaid4se4.entities.Payement;
import tn.rahma.newprojectrahmabensaid4se4.entities.PayementStatus;
import tn.rahma.newprojectrahmabensaid4se4.entities.PayementType;
import tn.rahma.newprojectrahmabensaid4se4.entities.Student;
import tn.rahma.newprojectrahmabensaid4se4.repository.PayementRepo;
import tn.rahma.newprojectrahmabensaid4se4.repository.StudentRepo;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
public class PayementRestcontroller {
    private StudentRepo studentRepo;
    private PayementRepo payementRepo;

    public PayementRestcontroller(StudentRepo studentRepo, PayementRepo payementRepo) {
        this.studentRepo = studentRepo;
        this.payementRepo = payementRepo;
    }
    @GetMapping(path = "/payements")
    public List<Payement>allPayements(){return payementRepo.findAll();}


    @GetMapping(path = "/students/{code}/payements")
    public List<Payement>payementsbystudent(@PathVariable String code){return payementRepo.findByStudent_Code(code);}

    @GetMapping(path = "/payementsbystatus/{status}")
    public List<Payement>payementsbystatus(@PathVariable PayementStatus status){return payementRepo.findByStatus(status);}


    @GetMapping(path = "/payementsbystatus/{type}")
    public List<Payement>payementsbystatus(@PathVariable PayementType type ){return payementRepo.findByType(type);}


    @GetMapping(path = "/payementbyids/{id}")
    public Payement payementbyid(@PathVariable Long id ){return payementRepo.findById(id).get();}


    @GetMapping(path = "/students")
    public List<Student>findstudents(){return studentRepo.findAll();}


    @GetMapping(path = "/studentbycode/{code}")
    public Student findstudentbycode(@PathVariable String code ){return studentRepo.findByCode(code);}


    @GetMapping(path = "/studentbyprogramId/{programId}")
    public List<Student>  findstudentbyprogramId(@PathVariable String programId ){return studentRepo.findByProgramId(programId);}

    @PutMapping(path = "/updatepayement/{id}")
    public Payement updatePayementStatus(@RequestParam PayementStatus status,@PathVariable Long id){
        Payement payement=payementRepo.findById(id).orElse(null);
        payement.setStatus(status);
        return payementRepo.save(payement);
    }
    @PostMapping(path = "/savepayement", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payement savepayement(@RequestParam MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
      Path folderPath= Paths.get(System.getProperty("user.home"),"rahma-data","payments");
      if (!Files.exists(folderPath)){
          Files.createDirectories(folderPath);
      }
      String fileName= UUID.randomUUID().toString();
        Path filePath= Paths.get(System.getProperty("user.home"),"rahma-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
     Student tudent =studentRepo.findByCode(newPaymentDTO.getStudentcode());
        Payement payement=Payement.builder().date(newPaymentDTO.getDate()).amount(newPaymentDTO.getAmount()).type(newPaymentDTO.getType()).student(tudent).file(filePath.toUri().toString()).status(PayementStatus.CREATED).build();
        return payementRepo.save(payement);
    }
    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getPaymentFile(@PathVariable Long paymentId) {
        try {
            // Recherche du paiement avec l'ID donné
            Payement p = payementRepo.findById(paymentId).orElse(null);
            if (p == null) {
                // Si le paiement n'est pas trouvé
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Payment not found with ID: " + paymentId);
            }

            // Récupération du chemin du fichier depuis l'objet Payement
            String filePathStr = p.getFile();
            if (filePathStr == null || filePathStr.isEmpty()) {
                // Si le chemin est manquant ou invalide
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("File path is missing or invalid for Payment ID: " + paymentId);
            }

            // Convertir l'URI en chemin absolu du fichier
            URI fileUri = URI.create(filePathStr);
            Path path = Paths.get(fileUri);

            // Vérifier si le fichier existe à cet emplacement
            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found at: " + filePathStr);
            }

            // Lire le contenu du fichier PDF
            byte[] fileContent = Files.readAllBytes(path);

            // Retourner le fichier PDF en réponse
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(fileContent);

        } catch (Exception e) {
            // En cas d'exception, loguer l'erreur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching file: " + e.getMessage());
        }
    }




}
