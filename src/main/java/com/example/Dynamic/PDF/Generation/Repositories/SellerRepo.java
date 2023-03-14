package com.example.Dynamic.PDF.Generation.Repositories;

import com.example.Dynamic.PDF.Generation.Entities.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepo extends CrudRepository<Seller,Integer> {
}
