package com.example.Dynamic.PDF.Generation.Controller;

import com.example.Dynamic.PDF.Generation.Dtos.BillResponse;
import com.example.Dynamic.PDF.Generation.Service.impl.BuyerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/buyer")
public class GetPdfController {
    @Autowired
    BuyerServiceImpl buyerService;
    @Autowired
    ServletContext servletContext;
    @GetMapping(value = "/getBillById")
    public String getBillById(Model model){
        BillResponse billResponse = buyerService.getBillById();
        model.addAttribute("billResponse",billResponse);
        return "views/billResponse";
    }
    @GetMapping("/createPdf")
    public void createPdf(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        BillResponse billResponse = buyerService.getBillById();
        boolean isFlag = buyerService.createPdf(billResponse,servletContext,httpServletResponse,httpServletRequest);

        if (isFlag){
            String fullPath = httpServletRequest.getServletContext().getRealPath("/resources/reports/"+"bill"+".pdf");
            filedownload(fullPath,httpServletResponse,"bill.pdf");
        }
    }

    private void filedownload(String fullPath, HttpServletResponse httpServletResponse, String filename) {
        File file = new File(fullPath);
        final  int BUFFER_SIZE = 4096;
        if (file.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = servletContext.getMimeType(fullPath);
                httpServletResponse.setContentType(mimeType);
                httpServletResponse.setHeader("content-disposition","attachment; filename"+filename);
                ServletOutputStream outputStream = httpServletResponse.getOutputStream(); //////////
                byte[] buffer = new byte[BUFFER_SIZE];
                int byteRead = -1;
                while ((byteRead = inputStream.read(buffer))!=-1){
                    outputStream.write(buffer,0,byteRead);
                    inputStream.close();
                    outputStream.close();
                    file.delete();
                }
            }
            catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
