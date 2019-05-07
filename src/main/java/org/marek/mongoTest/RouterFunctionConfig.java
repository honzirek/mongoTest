package org.marek.mongoTest;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {
    @Autowired
    private UserRepository userRepository;

    @Bean
    public RouterFunction<?> helloRouterFunction() {
        return route(GET("/users"), this::users)
                .andRoute(GET("/save"), this::saveUser)
                .andRoute(GET("/byUsername"), this::byUsername)
                .andRoute(GET("/byUsernameLimit"), this::byUsernameLimit);
    }

    public Mono<ServerResponse> users (ServerRequest request) {
//        QUser person = new QUser("user");
//        Predicate predicate = user.firstname.equalsIgnoreCase("dave")
//                .and(user.lastname.startsWithIgnoreCase("mathews"));
        return ServerResponse.ok().body(userRepository.findAll().take(12).doOnError(x -> x.printStackTrace()), User.class);
    }

    public Mono<ServerResponse> saveUser (ServerRequest request) {
        return ServerResponse.ok().body(userRepository.pushMethod(request.queryParam("id").get())
                .doOnError(x -> x.printStackTrace()), UpdateResult.class);
    }

    public Mono<ServerResponse> byUsername (ServerRequest request) {
        return ServerResponse.ok().body(userRepository.findByTheUsersUsername(request.queryParam("username").get(), new PageRequest(1, 2))
                .doOnError(x -> x.printStackTrace()), User.class);
    }

    public Mono<ServerResponse> byUsernameLimit (ServerRequest request) {
        return ServerResponse.ok().body(userRepository.najdeLimit(request.queryParam("username").get())
                .doOnError(x -> x.printStackTrace()), User.class);
    }
}
