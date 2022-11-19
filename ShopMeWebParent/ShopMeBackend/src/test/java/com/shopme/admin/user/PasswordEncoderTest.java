package com.shopme.admin.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "dummy";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        Assertions.assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }
}
