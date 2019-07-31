package org.marek.mongoTest.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.marek.mongoTest.User;
import org.marek.mongoTest.mongorepo.UserRepository;

import java.util.concurrent.CompletableFuture;

public class Mutation implements GraphQLMutationResolver {
    private UserRepository userRepository;

    public Mutation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<User> newUser(String firstName, String lastName) {
        User author = new User(null, firstName, lastName, null, null, null, null, null, null, null, null);

        return userRepository.save(author).toFuture();
    }
}
