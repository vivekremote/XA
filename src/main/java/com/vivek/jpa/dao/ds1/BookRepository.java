package com.vivek.jpa.dao.ds1;


import com.vivek.jpa.entity.ds1.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByName(String name);

}
