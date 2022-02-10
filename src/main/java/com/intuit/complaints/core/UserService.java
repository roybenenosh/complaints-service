package com.intuit.complaints.core;

import com.intuit.complaints.dal.User;

import java.util.UUID;

public interface UserService {

    User getUser(UUID userId);

}
