package org.marek.mongoTest.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.marek.mongoTest.User;
import org.marek.mongoTest.elasticrepo.UserElasticRepository;
import org.marek.mongoTest.mongorepo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Query implements GraphQLQueryResolver {
    private UserRepository userRepository;
    private UserElasticRepository userElasticRepository;

    public Query(UserRepository userRepository, UserElasticRepository userElasticRepository) {
        this.userRepository = userRepository;
        this.userElasticRepository = userElasticRepository;
    }

    public CompletableFuture<List<User>> findAllUsers() {
        return userRepository.findAll().collectList().toFuture();
    }

//    public Flux<User> findAllUsers() {
//        return userRepository.findAll();
//    }


    public CompletableFuture<Long> countUsers() {
        Page<User> userPage = userElasticRepository.findByUsername("Marek", PageRequest.of(0, 10));
        userPage.get().forEach(System.out::println);

        return userRepository.count().toFuture();
    }

}
