package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Collection;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithSingleRole() {
        Role adminRole = entityManager.find(Role.class, 1);
        User user = new User("sa@sa.cz", "dummy", "Oleg", "Y");
        user.addRole(adminRole);

        User persistedUser = userRepository.save(user);

        Assertions.assertThat(persistedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles() {
        User user = new User("sa1@sa.com", "dummy", "Dummy", "User");
        user.addRole(new Role(3));
        user.addRole(new Role(5));

        User persistedUser = userRepository.save(user);

        Assertions.assertThat(persistedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> userList = userRepository.findAll();
        userList.forEach(System.out::println);
        Assertions.assertThat(((Collection<?>) userList).size()).isGreaterThan(0);
    }

    @Test
    public void testGetUserByUd() {
        User user = userRepository.findById(1).orElse(null);
        System.out.println(user);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User user = userRepository.findById(1).orElse(null);

        Assertions.assertThat(user).isNotNull();

        user.setEnabled(true);
        user.setEmail("oy@cz.cz");
        User persistedUser = userRepository.save(user);

        Assertions.assertThat(persistedUser.isEnabled()).isTrue();
        Assertions.assertThat(persistedUser.getEmail()).isEqualTo("oy@cz.cz");
    }

    @Test
    public void testUpdateUserRoles() {
        User user = userRepository.findById(2).orElse(null);

        Assertions.assertThat(user).isNotNull();

        user.getRoles().remove(new Role(3));
        user.addRole(new Role(2));

        User persistedUser = userRepository.save(user);

        Assertions.assertThat(persistedUser.getRoles()).contains(new Role(2));
    }

    @Test
    public void testDeleteUser() {
        User user = userRepository.findById(2).orElse(null);

        Assertions.assertThat(user).isNotNull();

        userRepository.deleteById(user.getId());

        Assertions.assertThat(userRepository.findById(2).orElse(null)).isNull();
    }
}
