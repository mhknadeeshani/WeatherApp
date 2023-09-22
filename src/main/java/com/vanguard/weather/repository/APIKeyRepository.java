package com.vanguard.weather.repository;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class APIKeyRepository {

    private static final Set<String> apiKeys = new HashSet<>();

    static {
        apiKeys.add("821ea665e94bc5a0ee2456c41de5f3ea");
        apiKeys.add("f7e36d8ed0a99c3a7e16c991e981e042");
        apiKeys.add("cc4333bd166205c43e82d3b7d70e152b");
        apiKeys.add("ef280d1eb57382537cc8bd98201219af");
        apiKeys.add("01a3619e5bc7cfa0dc5d8139117f0ba1");
    }


    public boolean isValidAPIKey(String apiKey) {
        return apiKeys.contains(apiKey);
    }

}
