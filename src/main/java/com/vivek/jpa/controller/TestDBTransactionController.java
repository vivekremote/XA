package com.vivek.jpa.controller;

import com.vivek.jpa.dao.ds1.BookRepository;
import com.vivek.jpa.dao.ds2.UserRepository;
import com.vivek.jpa.entity.ds1.Book;
import com.vivek.jpa.entity.ds2.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class TestDBTransactionController {

    static int i = 0;
    static long bookId = 1;
    static long userId = 1;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/testTransactionForDB")
    public void testTransactionForDB() {
        dbOperationForDS1();
        dbOperationForDS2();

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void dbOperationForDS1() {
        bookRepository.save(new Book(bookId++, "Java"));
        bookRepository.save(new Book(bookId++, "Node"));
        bookRepository.save(new Book(bookId++, "Python"));

        System.out.println("\nfindAll()");
        bookRepository.findAll().forEach(x -> System.out.println(x));

        System.out.println("\nfindById(1L)");
        bookRepository.findById(1l).ifPresent(x -> System.out.println(x));

        System.out.println("\nfindByName('Node')");
        bookRepository.findByName("Node").forEach(x -> System.out.println(x));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void dbOperationForDS2() {
        userRepository.save(new User(userId++, "Tamil"));
        userRepository.save(new User(userId++, "Aara"));
        userRepository.save(new User(userId++, "Aadhira"));

        System.out.println("\nfindAll()");
        userRepository.findAll().forEach(x -> System.out.println(x));

        System.out.println("\nfindById(1L)");
        userRepository.findById(1l).ifPresent(x -> System.out.println(x));

        System.out.println("\nfindByName('Node')");
        userRepository.findByName("Node").forEach(x -> System.out.println(x));

        if(i > 2) {
            int a = 10/0;
            System.out.println(a);
        }
        i++;

    }
}
