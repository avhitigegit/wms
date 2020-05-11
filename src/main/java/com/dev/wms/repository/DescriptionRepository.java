package com.dev.wms.repository;

import com.dev.wms.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, String> {

    List<Description> findByUserSeqAndStatusSeq(String userSeq, Integer statusSeq);

    Description findByDescriptionSeqAndStatusSeq(String descriptionSeq, Integer statusSeq);

}
