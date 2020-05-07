package com.dev.wms.repository;


import com.dev.wms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByUserSeqAndStatusSeq(String userSeq, Integer statusSeq);

    User findByEmailAndStatusSeq(String email, Integer statusSeq);
}
