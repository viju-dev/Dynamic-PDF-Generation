package com.example.Dynamic.PDF.Generation.Repositories;

import com.example.Dynamic.PDF.Generation.Entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends CrudRepository<Item,Integer> {
}
