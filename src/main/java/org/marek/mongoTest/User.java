package org.marek.mongoTest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@AllArgsConstructor
@Document
@org.springframework.data.elasticsearch.annotations.Document(indexName = "comp", type = "user")
@JsonIgnoreProperties( value = {"email"} )
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String username;

    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;

    @Version
    Long version;
}
