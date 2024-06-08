package com.example.demo;

import com.example.demo.dto.UserCreateRequest;
import com.example.demo.dto.UserCreateResponse;
import com.example.demo.helpers.DataHelper;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTests extends TestBase {

    @Autowired
    private UserService userService;

    @Test
    void createUser() {
        final int countUserBefore = getEntriesCount(TABLE_NAME_USERS);
        final String userName = DataHelper.getAlphabeticString(10);
        final UserCreateResponse testUser = userService
            .createUser(new UserCreateRequest(userName));
        final int countUserAfter = getEntriesCount(TABLE_NAME_USERS);
        assertThat(countUserAfter - countUserBefore)
            .isOne();

        assertThat(testUser)
            .isNotNull();
        assertThat(testUser.userId())
            .isPositive();
        assertThat(testUser.username())
            .isEqualTo(userName);
    }

    /*@Test
    void deleteUser() {
        //final String name = UUID.randomUUID().toString();

        final int countUserBefore = getEntriesCount(TABLE_NAME_USERS);
        //final UserDto UserDto = UserService.deleteUser(testUser.id());
        final int countUserAfter = getEntriesCount(TABLE_NAME_USERS);
    }*/
}
