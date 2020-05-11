package com.dev.wms.repository;

import com.dev.wms.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

    List<Contact> findByUserSeqAndStatusSeq(String userSeq, Integer statusSeq);

    Contact findByContactSeqAndStatusSeq(String contactSeq, Integer statusSeq);

}
