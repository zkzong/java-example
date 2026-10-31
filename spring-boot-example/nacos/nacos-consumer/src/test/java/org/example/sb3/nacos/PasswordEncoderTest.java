package org.example.sb3.nacos;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void encode() {
        System.out.println(new BCryptPasswordEncoder().encode("nacos"));
    }

}
