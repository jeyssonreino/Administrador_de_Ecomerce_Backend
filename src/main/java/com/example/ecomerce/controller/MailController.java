package com.example.ecomerce.controller;

import com.example.ecomerce.models.EmailDTO;
import com.example.ecomerce.models.EmailWithFileDTO;
import com.example.ecomerce.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api")
public class MailController {

    @Autowired
    private IEmailService iEmailService;

// Controlador para mandar un correo electronico pasandole los correos destinatarios, el asunto y el texto del correo
    @PostMapping("/enviar-email")
    public ResponseEntity<?>enviarEmail(@RequestBody EmailDTO emailDTO){
        try {
            iEmailService.sendEmail(emailDTO.getToUser(),emailDTO.getSubject(),emailDTO.getMessage());
            return ResponseEntity.ok("mail enviado");
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // Controlador para mandar un correo electronico con archivo adjunto, pasandole los correos destinatarios, el asunto, el texto del correo y el archivo adjunto
    @PostMapping("/enviar-email-con-archivo")
    public ResponseEntity<?> enviarEmailConArchivo(@ModelAttribute EmailWithFileDTO emailFileDTO){

        try {
            String fileName = emailFileDTO.getFile().getOriginalFilename();
            Path path = Paths.get("src/main/resources/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(emailFileDTO.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            File file = path.toFile();

            iEmailService.sendEmailWithFile(emailFileDTO.getToUser(), emailFileDTO.getSubject(), emailFileDTO.getMessage(), file);
            boolean fileDeleted = file.delete();

            return ResponseEntity.ok("Enivado mail con archivo adjunto");

        } catch (Exception e){
            throw new RuntimeException("Error al enviar el Email con el archivo. " + e.getMessage());
        }
    }


}
