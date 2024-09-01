package org.example.ecommercefashion.repositories.mysql;

import org.example.ecommercefashion.entities.mysql.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
