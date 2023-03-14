package com.example.Dynamic.PDF.Generation.Service.impl;

import com.example.Dynamic.PDF.Generation.Dtos.BillResponse;
import com.example.Dynamic.PDF.Generation.Entities.Buyer;
import com.example.Dynamic.PDF.Generation.Entities.Item;
import com.example.Dynamic.PDF.Generation.Entities.PurchasedItems;
import com.example.Dynamic.PDF.Generation.Entities.Seller;
import com.example.Dynamic.PDF.Generation.Repositories.BuyerRepo;
import com.example.Dynamic.PDF.Generation.Repositories.ItemRepo;
import com.example.Dynamic.PDF.Generation.Repositories.SellerRepo;
import com.example.Dynamic.PDF.Generation.Service.BuyerService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    BuyerRepo buyerRepo;
    @Autowired
    ItemRepo itemRepo;
    @Autowired
    SellerRepo sellerRepo;

    public BillResponse getBillById() {
        int id = 1;
        BillResponse billResponse = new BillResponse();
        Buyer buyer = buyerRepo.findById(id).get();
        int sellerId = buyer.getId();
        Seller seller = sellerRepo.findById(sellerId).get();
//        List<Item> itemList = buyer.getItemList();
        billResponse.setSellerName(seller.getName());
        billResponse.setSellerAddress(seller.getAddress());
        billResponse.setSellerGstNo(seller.getGstIn());

        billResponse.setBuyerName(buyer.getName());
        billResponse.setBuyerAddress(buyer.getAddress());
        billResponse.setBuyerGstNo(buyer.getGstIn());

        billResponse.setItemList(buyer.getPurchasedItemsList());

    return  billResponse;

    }

    public boolean createPdf(BillResponse billResponse, ServletContext context, HttpServletResponse response, HttpServletRequest request) {
        Document document = new Document(PageSize.A4,15,15,45,30);//margin l,r,t,b
        try {
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if(!exists){
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(file+"/"+"bill"+".pdf"));
            document.open();

            Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
//           title of bill
            Paragraph paragraph = new Paragraph("Generated Bill",mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(6);
            document.add(paragraph);

//            main Info table
            PdfPTable pTable1 = new PdfPTable(2);
            pTable1.setWidthPercentage(100);
            pTable1.setSpacingBefore(10f);
            pTable1.setSpacingAfter(10);
            Font pTable1Body = FontFactory.getFont("Open Sans",15,BaseColor.BLACK);
            float[] pTable1columnsWidth = {4f,4f};
            pTable1.setWidths(pTable1columnsWidth);
            PdfPCell cell1 = new PdfPCell();
            cell1.addElement(new Paragraph("Seller :"));
            cell1.addElement(new Paragraph(billResponse.getSellerName()));
            cell1.addElement(new Paragraph(billResponse.getSellerAddress()));
            cell1.addElement(new Paragraph(billResponse.getSellerGstNo()));
//            cell1.setPaddingLeft(10);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setPadding(20);

            pTable1.addCell(cell1);

            PdfPCell cell2 = new PdfPCell();
            cell2.addElement(new Paragraph("Buyer :"));
            cell2.addElement(new Paragraph(billResponse.getBuyerName()));
            cell2.addElement(new Paragraph(billResponse.getBuyerAddress()));
            cell2.addElement(new Paragraph(billResponse.getBuyerGstNo()));

            cell2.setVerticalAlignment(Element.ALIGN_CENTER);
            cell2.setPadding(20);

            pTable1.addCell(cell2);
            pTable1.setSpacingAfter(0);
            document.add(pTable1);

            //
            PdfPTable pTable = new PdfPTable(4);
            pTable.setWidthPercentage(100);
            pTable.setSpacingBefore(10f);
            pTable.setSpacingAfter(10f);

            Font tableHeader = FontFactory.getFont("Arial",10,BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial",10,BaseColor.BLACK);

            float[] columnsWidth = {3f,2f,1f,2f};
            pTable.setWidths(columnsWidth);

            //header columns
            PdfPCell Item = new PdfPCell(new Paragraph("Item",tableHeader));
            Item.setBorderColor(BaseColor.BLACK);
            Item.setPaddingLeft(10);
            Item.setHorizontalAlignment(Element.ALIGN_CENTER);
            Item.setVerticalAlignment(Element.ALIGN_CENTER);
            Item.setBackgroundColor(BaseColor.GRAY);
            Item.setExtraParagraphSpace(5f);
            pTable.addCell(Item);

            PdfPCell Quantity = new PdfPCell(new Paragraph("Quantity",tableHeader));
            Quantity.setBorderColor(BaseColor.BLACK);
            Quantity.setPaddingLeft(10);
            Quantity.setHorizontalAlignment(Element.ALIGN_CENTER);
            Quantity.setVerticalAlignment(Element.ALIGN_CENTER);
            Quantity.setBackgroundColor(BaseColor.GRAY);
            Quantity.setExtraParagraphSpace(5f);
            pTable.addCell(Quantity);

            PdfPCell Rate = new PdfPCell(new Paragraph("Rate",tableHeader));
            Rate.setBorderColor(BaseColor.BLACK);
            Rate.setPaddingLeft(10);
            Rate.setHorizontalAlignment(Element.ALIGN_CENTER);
            Rate.setVerticalAlignment(Element.ALIGN_CENTER);
            Rate.setBackgroundColor(BaseColor.GRAY);
            Rate.setExtraParagraphSpace(5f);
            pTable.addCell(Rate);

            PdfPCell Amount = new PdfPCell(new Paragraph("Amount",tableHeader));
            Amount.setBorderColor(BaseColor.BLACK);
            Amount.setPaddingLeft(10);
            Amount.setHorizontalAlignment(Element.ALIGN_CENTER);
            Amount.setVerticalAlignment(Element.ALIGN_CENTER);
            Amount.setBackgroundColor(BaseColor.GRAY);
            Amount.setExtraParagraphSpace(5f);
            pTable.addCell(Amount);

//              loop to add data row  in body
            for (PurchasedItems prodduct : billResponse.getItemList()){
                PdfPCell ItemVal = new PdfPCell(new Paragraph(prodduct.getItem().getName(),tableBody));
                ItemVal.setBorderColor(BaseColor.BLACK);
                ItemVal.setPaddingLeft(10);
                ItemVal.setHorizontalAlignment(Element.ALIGN_CENTER);
                ItemVal.setVerticalAlignment(Element.ALIGN_CENTER);
                ItemVal.setBackgroundColor(BaseColor.WHITE);
                ItemVal.setExtraParagraphSpace(5f);
                pTable.addCell(ItemVal);

                PdfPCell QuantityVal = new PdfPCell(new Paragraph(prodduct.getQuantity()+" Nos",tableBody));
                QuantityVal.setBorderColor(BaseColor.BLACK);
                QuantityVal.setPaddingLeft(10);
                QuantityVal.setHorizontalAlignment(Element.ALIGN_CENTER);
                QuantityVal.setVerticalAlignment(Element.ALIGN_CENTER);
                QuantityVal.setBackgroundColor(BaseColor.WHITE);
                QuantityVal.setExtraParagraphSpace(5f);
                pTable.addCell(QuantityVal);

                PdfPCell RateVal = new PdfPCell(new Paragraph(String.valueOf(prodduct.getItem().getRate()),tableBody));
                RateVal.setBorderColor(BaseColor.BLACK);
                RateVal.setPaddingLeft(10);
                RateVal.setHorizontalAlignment(Element.ALIGN_CENTER);
                RateVal.setVerticalAlignment(Element.ALIGN_CENTER);
                RateVal.setBackgroundColor(BaseColor.WHITE);
                RateVal.setExtraParagraphSpace(5f);
                pTable.addCell(RateVal);

                PdfPCell AmountVal = new PdfPCell(new Paragraph(String.valueOf(prodduct.getTotal()),tableBody));
                AmountVal.setBorderColor(BaseColor.BLACK);
                AmountVal.setPaddingLeft(10);
                AmountVal.setHorizontalAlignment(Element.ALIGN_CENTER);
                AmountVal.setVerticalAlignment(Element.ALIGN_CENTER);
                AmountVal.setBackgroundColor(BaseColor.WHITE);
                AmountVal.setExtraParagraphSpace(5f);
                pTable.addCell(AmountVal);
            }

//          Footer row
            double totalAmt = 0;
            for (PurchasedItems items:billResponse.getItemList()){
                totalAmt += items.getTotal();
            }
            PdfPCell blankCell1 = new PdfPCell();
            blankCell1.setPaddingLeft(10);
            blankCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            blankCell1.setVerticalAlignment(Element.ALIGN_CENTER);
            blankCell1.setBackgroundColor(BaseColor.WHITE);
            blankCell1.setBorderWidthRight(0);
            blankCell1.setExtraParagraphSpace(5f);
            pTable.addCell(blankCell1);

            PdfPCell blankCell2 = new PdfPCell();
            blankCell2.setPaddingLeft(10);
            blankCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            blankCell2.setVerticalAlignment(Element.ALIGN_CENTER);
            blankCell2.setBackgroundColor(BaseColor.WHITE);
            blankCell1.setBorderWidthRight(0);
            blankCell2.setExtraParagraphSpace(5f);
            pTable.addCell(blankCell2);

            PdfPCell total = new PdfPCell(new Paragraph("Total",tableBody));
            total.setPaddingLeft(10);
            total.setHorizontalAlignment(Element.ALIGN_CENTER);
            total.setVerticalAlignment(Element.ALIGN_CENTER);
            total.setBackgroundColor(BaseColor.WHITE);
            total.setExtraParagraphSpace(5f);
            pTable.addCell(total);

            PdfPCell totalVal = new PdfPCell(new Paragraph(String.valueOf(totalAmt),tableBody));
            totalVal.setPaddingLeft(10);
            totalVal.setHorizontalAlignment(Element.ALIGN_CENTER);
            totalVal.setVerticalAlignment(Element.ALIGN_CENTER);
            totalVal.setBackgroundColor(BaseColor.WHITE);
            totalVal.setExtraParagraphSpace(5f);
            pTable.addCell(totalVal);



            pTable.setSpacingBefore(0);
            document.add(pTable);
            document.close();
            writer.close();
            return true;

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
