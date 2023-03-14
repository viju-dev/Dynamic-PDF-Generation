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
public class PurchasedItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double total;

    @ManyToOne
    @JoinColumn
    private Buyer buyer;

    @ManyToOne
    @JoinColumn
    private Bill bill;
    @OneToOne
    @JoinColumn
    private Item item;

}
