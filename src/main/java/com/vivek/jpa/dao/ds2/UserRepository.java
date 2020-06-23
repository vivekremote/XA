package com.vivek.jpa.dao.ds2;


import com.vivek.jpa.entity.ds1.Book;
import com.vivek.jpa.entity.ds2.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<Book> findByName(String name);

}
