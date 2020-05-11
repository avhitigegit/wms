package com.dev.wms.repository;

import com.dev.wms.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    List<Image> findByUserSeqAndStatusSeq(String userSeq, Integer statusSeq);

    Image findByImageSeqAndStatusSeq(String imageSeq, Integer statusSeq);

}
