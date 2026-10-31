package org.example.sb3.ss.repository;

import org.example.sb3.ss.entity.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void selectAll() {
        List<Address> addresses = addressRepository.selectAll();
        System.out.println(addresses);
    }

}
