package org.example.ecommercefashion.common.auth.repository;

import org.example.ecommercefashion.common.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
