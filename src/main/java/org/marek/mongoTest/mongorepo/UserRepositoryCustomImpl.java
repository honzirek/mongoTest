package org.marek.mongoTest.mongorepo;

import com.mongodb.client.result.UpdateResult;
import org.marek.mongoTest.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    protected ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<UpdateResult> pushMethod(String objectId) {
        return reactiveMongoTemplate.inTransaction().execute(action -> {
            Mono<User> removedUser = action.findAndRemove(Query.query(Criteria.where("city").is("Test Push City")), User.class);
            removedUser.subscribe(user -> System.out.println("removedUser: " + user));

            Mono<UpdateResult> updateResultFlux = action.updateFirst(Query.query(Criteria.where("id").is(objectId)),
                    new Update().set("city", "Test Push City"), User.class);

            if (false) {
                throw new RuntimeException("testing");
            }

            return updateResultFlux;
        });
    }

    @Override
    public Flux<User> najdeLimit(String username) {
        return reactiveMongoTemplate.find(Query.query(Criteria.where("username").is(username)).limit(2), User.class);
    }
}
