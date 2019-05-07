package org.marek.mongoTest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String>, UserRepositoryCustom/*, QuerydslPredicateExecutor<User>*/ {

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);

    @Query(value="{ 'username' : ?0 }", fields="{ 'fullname' : 1, 'email' : 1}")
    Flux<User> findByTheUsersUsername(String username, Pageable pageable);

}
