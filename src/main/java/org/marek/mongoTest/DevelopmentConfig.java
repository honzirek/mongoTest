package org.marek.mongoTest;

import com.mongodb.Mongo;
import com.mongodb.reactivestreams.client.Success;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.schema.JsonSchemaProperty.string;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepo, Mongo mongo, ReactiveMongoOperations operations, ReactiveMongoTemplate mongoTemplate) {

        MongoJsonSchema schema = MongoJsonSchema.builder()
                .required("username", "password")
                .properties(
                        string("firstname"),
                        string("password"))
                .build();

        return args -> {
           //mongoTemplate.dropCollection("user").block();
            operations.collectionExists(User.class)
                    .flatMap(exists -> exists ? operations.dropCollection(User.class) /*.then(Mono.just(true))*/ : Mono.just(exists))
                    .then(operations.createCollection(User.class, CollectionOptions.empty().schema(schema)))
                    .then(/*o -> {
                        System.out.println("transforming");
                        return*/ userRepo.save(new User(null, "Marek", "password",
                            "Craig Walls", "123 North Street", "Cross Roads", "TX",
                            "76227", "123-123-1234", "craig@habuma.com", null))) /*;}*/
                    .doOnError(e -> System.out.println("test")).then().block();

        };
    }
}
