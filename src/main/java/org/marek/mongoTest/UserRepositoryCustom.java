package org.marek.mongoTest;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepositoryCustom {
    Flux<UpdateResult> pushMethod(String objectId);
    Flux<User> najdeLimit(String username);
}
