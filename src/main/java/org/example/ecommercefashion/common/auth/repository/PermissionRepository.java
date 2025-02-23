package org.example.ecommercefashion.common.auth.repository;

import org.example.ecommercefashion.common.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query(
            "SELECT p.name FROM Permission p "
                    + "JOIN p.roles r "
                    + "JOIN r.users u "
                    + "WHERE u.id = :userId")
    Set<String> findAllPermissionsUserByRBAC(@Param("userId") long userId);


}
