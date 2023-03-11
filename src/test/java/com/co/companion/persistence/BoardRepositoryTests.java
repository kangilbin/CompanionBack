package com.co.companion.persistence;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.Arrays;
import java.util.Map;


@DataJpaTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testFindPageList() {
        Pageable pageable = PageRequest.of(0,10,  Sort.Direction.DESC, "reg_time");

        Page<Map> result = boardRepository.findPageList("",pageable);

        result.get().forEach(row -> {
            System.out.println(row.toString());
        });

    }
}
