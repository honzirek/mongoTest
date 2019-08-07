package org.marek.mongoTest;

import com.mongodb.Mongo;
import org.marek.mongoTest.elasticrepo.UserElasticRepository;
import org.marek.mongoTest.mongorepo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.completion.Completion;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.schema.JsonSchemaProperty.string;

@Profile("!prod")
@Configuration
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepo, Mongo mongo, ReactiveMongoOperations operations,
                                        ReactiveMongoTemplate mongoTemplate, UserElasticRepository userElasticRepository,
                                        ElasticsearchTemplate elasticsearchTemplate) {

        MongoJsonSchema schema = MongoJsonSchema.builder()
                .required("username", "password")
                .properties(
                        string("firstname"),
                        string("password"))
                .build();

        return args -> {
            elasticsearchTemplate.deleteIndex(User.class);
            elasticsearchTemplate.createIndex(User.class);
            elasticsearchTemplate.putMapping(User.class);
            User user1 = new User(null, new Completion(new String[] {"Matest"}), "password",
                    "Craig Walls", "123 North Street", "Cross Roads test", "TX",
                    "76227", "123-123-1234", "craig@habuma.com", null);
           //mongoTemplate.dropCollection("user").block();
            operations.collectionExists(User.class)
                    .flatMap(exists -> exists ? operations.dropCollection(User.class) /*.then(Mono.just(true))*/ : Mono.just(exists))
                    .then(operations.createCollection(User.class, CollectionOptions.empty().schema(schema)))
                    .then(/*o -> {
                        System.out.println("transforming");
                        return*/ userRepo.save(user1)) /*;}*/
                    .doOnError(e -> System.out.println("test")).then().block();

            System.out.println("id is " + user1.getId());
            userElasticRepository.save(user1);
        };
    }
}
