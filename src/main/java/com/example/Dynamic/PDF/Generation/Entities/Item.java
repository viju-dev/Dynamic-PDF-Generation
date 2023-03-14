package com.example.Dynamic.PDF.Generation.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private  double rate;
//    private String quantity;
//    private double amount;
    @ManyToOne
    @JoinColumn
    private Seller seller;

//    @ManyToOne
//    @JoinColumn
//    private Buyer buyer;

    @OneToOne(mappedBy = "item",cascade = CascadeType.ALL)
    private PurchasedItems purchasedItems;

}
