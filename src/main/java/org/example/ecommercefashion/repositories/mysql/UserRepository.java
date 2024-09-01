package org.example.ecommercefashion.repositories.mysql;

import org.example.ecommercefashion.entities.mysql.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    User findByEmail(String email);


    @Query("SELECT u.id FROM User u WHERE u.id IN :userIds")
    Set<Long> findByIdIn(@Param("userIds") Set<Long> userIds);

}
