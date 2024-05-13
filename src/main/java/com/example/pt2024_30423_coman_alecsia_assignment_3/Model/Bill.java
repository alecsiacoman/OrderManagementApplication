package com.example.pt2024_30423_coman_alecsia_assignment_3.Model;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ClientBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.OrderBLL;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.ProductBLL;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;


public record Bill(ClientBLL clientBLL, ProductBLL productBLL, OrderBLL orderBLL) {
    static int nrBill;
    public void makeBill() throws FileNotFoundException {
        List<Client> clientList;
        List<Product> productList;
        List<Orders> ordersList;

        clientList = clientBLL.viewAllClients();
        productList = productBLL.viewAllProducts();
        ordersList = orderBLL.viewAllOrders();

        nrBill++;
        try{
            Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream("Bill" + nrBill + ".pdf"));
            document.open();
            for(Client client : clientList){
                String info1 = new String();
                boolean ok = false;
                double totalPrice = 0;
                for(Orders orders : ordersList){
                    if(orders.getClientId() == client.getId()){
                        ok = true;
                        for(Product product : productList){
                            if(orders.getProductId() == product.getId()){
                                double productsPrice = orders.getQuantity() * product.getPrice();
                                if(orders.getQuantity() != 0)
                                {
                                    info1 += product.getName() + " -> " + product.getPrice() + " price/piece -> " + orders.getQuantity();
                                    if (orders.getQuantity() == 1) {
                                        info1 += " piece = ";
                                    } else info1 += " pieces = ";
                                    info1 += productsPrice + "\n";
                                    totalPrice += productsPrice;
                                }
                            }
                        }
                    }
                }

                if(ok){
                    String infoFinal = client.getName() + "\n" + info1 + "Total = " + totalPrice + "\n\n";
                    Paragraph paragraph = new Paragraph(infoFinal);
                    document.add(paragraph);
                }
            }
            document.close();
            AlertUtils.showMessage("Bill generated!");
        } catch (DocumentException | FileNotFoundException e) {
            AlertUtils.showAlert("Couldn't generate bill!");
        }

    }
}
