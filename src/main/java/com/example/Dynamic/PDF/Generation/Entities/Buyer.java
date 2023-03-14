package com.example.Dynamic.PDF.Generation.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String gstIn;
    private  String address;

    @OneToOne
    @JoinColumn
    private Seller seller;

//    @OneToMany(mappedBy = "buyer" , cascade = CascadeType.ALL)
//    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "buyer",cascade = CascadeType.ALL)
    private List<PurchasedItems> purchasedItemsList = new ArrayList<>();

    @OneToMany(mappedBy = "buyer",cascade = CascadeType.ALL)
    private List<Bill> billList = new ArrayList<>();
}
