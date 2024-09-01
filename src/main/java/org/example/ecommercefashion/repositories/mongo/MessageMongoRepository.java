package org.example.ecommercefashion.repositories.mongo;

import org.example.ecommercefashion.entities.mongo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageMongoRepository extends MongoRepository<Message, String> {


    @Query("{ 'senderId': ?0, 'receiverId': ?1 }")
    Page<Message> findAllMessageLatest(Long senderId, Long receiverId, Pageable pageable);
}
