package org.example.mongo.repository;

import org.example.mongo.entity.UserInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserInfo, Long> {
}
