package com.uexcel.eazyschool.rest;

import com.uexcel.eazyschool.model.Contact;
import com.uexcel.eazyschool.repository.ContactJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping(path = "/api/contact",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ContactRestController {
    private static final Logger log = LoggerFactory.getLogger(ContactRestController.class);
    private final ContactJpaRepository contactJpaRepository;

    @Autowired
    public ContactRestController(ContactJpaRepository contactJpaRepository) {
        this.contactJpaRepository = contactJpaRepository;
    }

    @GetMapping("/get-message-by-status")
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status") String  status) {
        return contactJpaRepository.findContactByStatusWithQuery(status);
    }

    @GetMapping("/get-message/{id}")
    public Contact getMessagesById(@PathVariable(name = "id") Long id) {
        return contactJpaRepository.findById(id).orElseThrow(() ->
                new CustomExceptionHandler(404, "not found"));
    }

    @PostMapping("/send-message")
    public ResponseEntity<Response> saveNewMessage(@Valid @RequestBody Contact contact, HttpServletRequest request) {
       Contact cont = contactJpaRepository.save(contact);
       log.info("******Client***************: {}", request.getHeader("invokedFrom"));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/get-message/" + cont.getId())
                .header("isMsgSaved", "true")
                .body(new Response(201,"success"));
    }

    @GetMapping("/delete-message")
    public ResponseEntity<Response> deleteMessage(@RequestParam(name = "id") Long id) {
      Optional<Contact> msg =  contactJpaRepository.findById(id);
      if (msg.isPresent()) {
          contactJpaRepository.delete(msg.get());
      } else {
          return ResponseEntity.status(404).body(new Response(404,"not found!"));
      }

       Optional<Contact> confirmDelete = contactJpaRepository.findById(id);

       if (confirmDelete.isPresent()) {
           return ResponseEntity.status(500).body(new Response(500,"Delete failed!"));
       }
        return ResponseEntity.ok()
                .body(new Response(200,"Deleted successfully!"));
    }
}
