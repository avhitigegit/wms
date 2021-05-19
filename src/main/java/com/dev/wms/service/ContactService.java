package com.dev.wms.service;

import com.dev.wms.common.CurrentUser;
import com.dev.wms.common.enums.Status;
import com.dev.wms.dto.ContactDto;
import com.dev.wms.exception.BadRequestException;
import com.dev.wms.exception.BadResponseException;
import com.dev.wms.model.Contact;
import com.dev.wms.repository.ContactRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    private static final Logger LOGGER = LogManager.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactDto saveContact(ContactDto contactDto) {
        LOGGER.info("Enter saveContact() in ContactService. " + CurrentUser.getUser().getEmail());
        try {
            if (contactDto != null) {
                Contact contact = new Contact();
                BeanUtils.copyProperties(contactDto, contact);
                Contact.initFrom(contact);
                Contact contactDb = this.contactRepository.save(contact);
                BeanUtils.copyProperties(contactDb, contactDto);
            }
            return contactDto;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void saveContactList(List<ContactDto> contactDtoList) {
        LOGGER.info("Enter saveContactList() in ContactService. " + CurrentUser.getUser().getEmail());
        try {
            if (contactDtoList != null) {
                for (ContactDto contactDto : contactDtoList) {
                    Contact contact = new Contact();
                    BeanUtils.copyProperties(contactDto, contact);
                    Contact.initFrom(contact);
                    this.contactRepository.save(contact);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<ContactDto> getContactList() {
        LOGGER.info("Enter getContactList() in ContactService. " + CurrentUser.getUser().getEmail());
        List<ContactDto> contactDtoList = new ArrayList<>();
        try {
            List<Contact> contactList = this.contactRepository.findByUserSeqAndStatusSeq(CurrentUser.getUser().getUserSeq(), Status.APPROVED.getStatusSeq());
            if (contactList != null) {
                for (Contact contact : contactList) {
                    ContactDto contactDto = new ContactDto();
                    BeanUtils.copyProperties(contact, contactDto);
                    contactDtoList.add(contactDto);
                }
            }
            return contactDtoList;
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
    }

    public ContactDto updateContact(ContactDto tempContactDto) {
        LOGGER.info("Enter updateContact() in ContactService. " + CurrentUser.getUser().getEmail());
        try {
            if (tempContactDto.getContactSeq() != null) {
                Contact contact = this.contactRepository.findByContactSeqAndStatusSeq(tempContactDto.getContactSeq(), Status.APPROVED.getStatusSeq());
                if (contact.getContactSeq() != null) {
                    contact.setContact_1(tempContactDto.getContact_1());
                    contact.setContact_2(tempContactDto.getContact_2());
                    contact.setContact_3(tempContactDto.getContact_3());
                    contact.setContactTypeSeq(tempContactDto.getContactTypeSeq());
                    contact.setStatusSeq(tempContactDto.getStatusSeq());
                    contact.setUserSeq(CurrentUser.getUser().getUserSeq());
                    this.contactRepository.save(contact);
                }
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return this.getSingleContactByID(tempContactDto.getContactSeq());
    }

    public ResponseEntity<Contact> deactivateContact(ContactDto contactDto) {
        LOGGER.info("Enter deactivateContact() in ContactService. " + CurrentUser.getUser().getEmail());
        try {
            ResponseEntity<Contact> responseEntity;
            Contact contact = this.contactRepository.findByContactSeqAndStatusSeq(contactDto.getContactSeq(), Status.APPROVED.getStatusSeq());
            if (contact != null) {
                contact.setStatusSeq(Status.DELETED.getStatusSeq());
                contact.setUserSeq(CurrentUser.getUser().getUserSeq());
                this.contactRepository.save(contact);
                responseEntity = new ResponseEntity<>(HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return responseEntity;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public ContactDto getSingleContactByID(String contactSeq) {
        LOGGER.info("Enter getSingleContact() in ContactService. " + CurrentUser.getUser().getEmail());
        ContactDto contactDto = new ContactDto();
        try {
            Contact contact = this.contactRepository.findByContactSeqAndStatusSeq(contactSeq, Status.APPROVED.getStatusSeq());
            if (contact != null) {
                BeanUtils.copyProperties(contact, contactDto);
            }
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
        return contactDto;
    }

    public List<ContactDto> getAllContactList() {
        LOGGER.info("Enter getAllContactList() in ContactService. " + CurrentUser.getUser().getEmail());
        List<ContactDto> contactDtoList = new ArrayList<>();
        try {
            List<Contact> contactList = this.contactRepository.findAll();
            if (contactList != null) {
                for (Contact contact : contactList) {
                    ContactDto contactDto = new ContactDto();
                    BeanUtils.copyProperties(contact, contactDto);
                    contactDtoList.add(contactDto);
                }
            }
            return contactDtoList;
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
    }
}
