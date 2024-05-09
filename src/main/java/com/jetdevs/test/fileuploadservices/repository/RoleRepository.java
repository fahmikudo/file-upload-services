package com.jetdevs.test.fileuploadservices.repository;

import com.jetdevs.test.fileuploadservices.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
