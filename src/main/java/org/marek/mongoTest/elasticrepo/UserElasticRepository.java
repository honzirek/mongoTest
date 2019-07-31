package org.marek.mongoTest.elasticrepo;

import org.marek.mongoTest.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserElasticRepository extends ElasticsearchRepository<User, String> {
    Page<User> findByUsername(String username, Pageable pageable);
}
