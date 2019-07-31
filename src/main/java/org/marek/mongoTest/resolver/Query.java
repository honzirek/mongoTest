package org.marek.mongoTest.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.marek.mongoTest.User;
import org.marek.mongoTest.mongorepo.UserRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Query implements GraphQLQueryResolver {
    private UserRepository userRepository;

    public Query(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<List<User>> findAllUsers() {
        return userRepository.findAll().collectList().toFuture();
    }

//    public Flux<User> findAllUsers() {
//        return userRepository.findAll();
//    }


    public CompletableFuture<Long> countUsers() {
        return userRepository.count().toFuture();
    }

}
