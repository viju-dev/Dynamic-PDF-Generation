package com.example.Dynamic.PDF.Generation.Repositories;

import com.example.Dynamic.PDF.Generation.Entities.Buyer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepo extends CrudRepository<Buyer,Integer> {
}
