package com.alexander.websitetest;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String>{
    public Account findByUser(String user);
}
