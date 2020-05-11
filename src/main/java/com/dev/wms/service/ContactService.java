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

    public void saveContact(ContactDto contactDto) {
        LOGGER.info("Enter saveContact() in AddressService.");
        try {
            if (contactDto != null) {
                Contact contact = new Contact();
                BeanUtils.copyProperties(contactDto, contact);
                Contact.initFrom(contact);
                this.contactRepository.save(contact);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void saveContactList(List<ContactDto> contactDtoList) {
        LOGGER.info("Enter saveContactList() in AddressService.");
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
        LOGGER.info("Enter getContactList() in AddressService.");
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
            throw new BadResponseException("Resource Not Found Exception.");
        }
    }

    public ContactDto updateContact(ContactDto tempContactDto) {
        LOGGER.info("Enter updateContact() in AddressService.");
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
        return this.getSingleContact(tempContactDto.getContactSeq());
    }

    public void deactivateContact(ContactDto contactDto) {
        LOGGER.info("Enter deactivateContact() in AddressService.");
        try {
            Contact contact = this.contactRepository.findByContactSeqAndStatusSeq(contactDto.getContactSeq(), Status.APPROVED.getStatusSeq());
            if (contact != null) {
                contact.setStatusSeq(Status.DELETED.getStatusSeq());
                contact.setUserSeq(CurrentUser.getUser().getUserSeq());
                this.contactRepository.save(contact);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    public ContactDto getSingleContact(String contactSeq) {
        LOGGER.info("Enter getSingleContact() in AddressService.");
        ContactDto contactDto = new ContactDto();
        try {
            Contact contact = this.contactRepository.findByContactSeqAndStatusSeq(contactSeq, Status.APPROVED.getStatusSeq());
            if (contact != null) {
                BeanUtils.copyProperties(contact, contactDto);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return contactDto;
    }
}
