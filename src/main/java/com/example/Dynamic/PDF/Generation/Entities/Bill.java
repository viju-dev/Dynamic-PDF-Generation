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
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String billNo;

    @ManyToOne
    @JoinColumn
    private Seller seller;

    @ManyToOne
    @JoinColumn
    private Buyer buyer;

    @OneToMany(mappedBy = "bill" , cascade = CascadeType.ALL)
    private List<PurchasedItems> itemList = new ArrayList<>();
}
