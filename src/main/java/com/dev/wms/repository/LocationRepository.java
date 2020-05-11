package com.dev.wms.repository;

import com.dev.wms.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {

    List<Location> findByUserSeqAndStatusSeq(String userSeq, Integer statusSeq);

    Location findByLocationSeqAndStatusSeq(String locationSeq, Integer statusSeq);

}
