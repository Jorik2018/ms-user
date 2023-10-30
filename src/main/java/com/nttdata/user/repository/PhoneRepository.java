package com.nttdata.user.repository;

import com.nttdata.user.model.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, String> {

}
