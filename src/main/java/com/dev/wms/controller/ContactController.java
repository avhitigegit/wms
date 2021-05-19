package com.dev.wms.controller;

import com.dev.wms.dto.ContactDto;
import com.dev.wms.service.ContactService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("contact")
public class ContactController {
    private static final Logger LOGGER = LogManager.getLogger(ContactController.class);

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping()
    public ResponseEntity createContact(@RequestBody ContactDto contactDto) {
        LOGGER.info("Enter createContact() in ContactController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.contactService.saveContact(contactDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @GetMapping
    public ResponseEntity<List<ContactDto>> getMyContactList() {
        LOGGER.info("Enter getMyContactList() in ContactController.");
        return ResponseEntity.ok().body(this.contactService.getContactList());
    }

    @GetMapping("{contactSeq}")
    public ResponseEntity<ContactDto> getContact(@PathVariable("contactSeq") String contactSeq) {
        LOGGER.info("Enter getContact() in ContactController.");
        return ResponseEntity.ok().body(this.contactService.getSingleContactByID(contactSeq));
    }

    @PutMapping()
    public ResponseEntity updateContact(@RequestBody ContactDto tempContactDto) {
        LOGGER.info("Enter updateContact() in ContactController.");
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(this.contactService.updateContact(tempContactDto), HttpStatus.CREATED);
        return ResponseEntity.ok().body(responseEntity);
    }

    @DeleteMapping
    public ResponseEntity deactivateContact(ContactDto contactDto) {
        LOGGER.info("Enter deactivateContact() in ContactController.");
        return ResponseEntity.ok().body(this.contactService.deactivateContact(contactDto));
    }

    //Admin
    @GetMapping("/all-contacts")
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        LOGGER.info("Enter getAllContacts() in ContactController.");
        return ResponseEntity.ok().body(this.contactService.getAllContactList());
    }

    @GetMapping("/active-contacts")
    public ResponseEntity<List<ContactDto>> getActiveContactList() {
        LOGGER.info("Enter getActiveContactList() in ContactController.");
        return ResponseEntity.ok().body(this.contactService.getAllContactList());
    }

    @GetMapping("/de-active-contacts")
    public ResponseEntity<List<ContactDto>> getDeActivateContactList() {
        LOGGER.info("Enter getDeActivateContactList() in ContactController.");
        return ResponseEntity.ok().body(this.contactService.getAllContactList());
    }
}
