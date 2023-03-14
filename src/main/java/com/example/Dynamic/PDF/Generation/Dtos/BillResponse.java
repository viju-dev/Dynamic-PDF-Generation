package com.example.Dynamic.PDF.Generation.Dtos;

import com.example.Dynamic.PDF.Generation.Entities.Item;
import com.example.Dynamic.PDF.Generation.Entities.PurchasedItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private String buyerName;
    private String buyerAddress;
    private String buyerGstNo;
    private String sellerName;
    private String sellerAddress;
    private String sellerGstNo;
//    private String itemName;
//    private String itemQuantity;
//    private double itemRate;
//    private double itemTotal;
    private List<PurchasedItems> itemList = new ArrayList<>();
}
