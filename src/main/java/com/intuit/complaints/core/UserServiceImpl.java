package com.intuit.complaints.core;

import com.intuit.complaints.dal.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {

    private final Map<UUID, User> users = Arrays.stream(new User[]{
        new User(UUID.fromString("a93adc57-4d59-4a9d-85c6-b5d48d99101d"), "John Doe", "johndoe@test.com", "Test Lane 1 Los Angeles"),
                new User(UUID.fromString("a872d86a-c7cb-48b7-b5d9-f218d6845405"), "John Smith", "johnsmith@test.com", "Test Lane 2 Los Angeles"),
                new User(UUID.fromString("dcb6a039-b0fc-49dd-b5de-58856f66727d"), "Jane Doe", "janedoe@test.com", "Test Lane 1 Los Angeles"),
                new User(UUID.fromString("bbbb080d-cffa-46d0-aa22-786c35d1a35b"), "Jane Smith", "janesmith@test.com", "Test Lane 2 Los Angeles"),
                new User(UUID.fromString("f22dff3f-06cf-49fe-97ec-bf7afe9a7fdb"), "Florence Hart", "florencehart@test.com", "Test Lane 3 Los Angeles"),
                new User(UUID.fromString("72dddc34-f058-4d31-b370-e88f772ea8e8"), "Antonio Smith", "antoniosmith@test.com", "Test Lane 4 Los Angeles"),
                new User(UUID.fromString("a90f6dd7-b74b-4be6-9065-daa1a92ba7ab"), "Derek Wong", "derekwong@test.com", "Test Lane 5 Los Angeles"),
                new User(UUID.fromString("a227c2bd-358a-4587-95f0-61fb63678952"), "Laverne Cohen", "lavernecohen@test.com", "Test Lane 6 Los Angeles"),
                new User(UUID.fromString("8145b0d6-feb2-4ff6-8546-c0a5eece6f82"), "Elizabeth Clark", "example@test.com", "Test Lane 7 Los Angeles")
    }).collect(Collectors.toMap(User::getId, u -> u));

    public UserServiceImpl() {
    }

    @Override
    public final User getUser(UUID id) {
        return users.get(id);
    }
}
