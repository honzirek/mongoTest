package org.marek.mongoTest;

import org.marek.mongoTest.mongorepo.UserRepository;
import org.marek.mongoTest.resolver.Mutation;
import org.marek.mongoTest.resolver.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages={"org.marek.mongoTest.elasticrepo"})
@EnableReactiveMongoRepositories(basePackages = {"org.marek.mongoTest.mongorepo"})
public class MongoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoTestApplication.class, args);
	}

	@Bean
	public Query query(UserRepository userRepository) {
		return new Query(userRepository);
	}

	@Bean
	public Mutation mutation(UserRepository userRepository) {
		return new Mutation(userRepository);
	}

//	@Bean
//	GraphQLSchema schema() {
//		return GraphQLSchema.newSchema()
//				.query(GraphQLObjectType.newObject()
//						.name("query")
//						.field(field -> field
//								.name("test")
//								.type(Scalars.GraphQLString)
//								.dataFetcher(environment -> "response")
//						)
//						.build())
//				.build();
//	}

}

