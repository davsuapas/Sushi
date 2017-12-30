package com.vida.sushi.authentication.web;

import com.elipcero.springsecurity.web.MongoDbUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@RequiredArgsConstructor
public class UserService {

    private @NonNull final MongoTemplate mongoTemplate;

    public void updateToken(String userName, String token) {
            mongoTemplate.updateFirst(
                new Query(where("userName").is(userName)),
                new Update().set("tokenId", token),
                MongoDbUserDetails.class
        );
    }
}
