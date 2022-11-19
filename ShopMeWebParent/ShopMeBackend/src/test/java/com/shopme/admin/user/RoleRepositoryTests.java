package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "manage everything");
        Role persistedRole = roleRepository.save(roleAdmin);

        Assertions.assertThat(persistedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRoles() {
        Role sales = new Role(
                "Salesperson", "Manage product price, customers, shipping, orders and sales report");
        Role editor = new Role("Editor", "Manage categories, brands, products, articles and menus");
        Role shipper = new Role("Shipper", "View products, orders, and update order status");
        Role assistant = new Role("Assistant", "Manage questions and reviews");
        roleRepository.saveAll(List.of(sales, editor, shipper, assistant));
    }
}
