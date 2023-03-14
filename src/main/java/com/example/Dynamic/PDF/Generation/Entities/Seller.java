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
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String gstIn;
    private  String address;

    @OneToOne(mappedBy = "seller",cascade = CascadeType.ALL)
    private Buyer buyer;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private List<Bill> billList = new ArrayList<>();
}
