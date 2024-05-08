package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.ClientDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.ProductDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Product;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
   // private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL(){
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id){
        Product product = productDAO.findById(id, "id");
        if(product == null){
            throw new NoSuchElementException("The client with id = " + id + " doesn't exist!");
        }
        return product;
    }

    public void insertProduct(Product product){
//        for(Validator<Product> item : validators)
//            item.validate(product);
        productDAO.insert(product);
    }

    public void editProduct(Product product){
//        for(Validator<Product> item : validators)
//            item.validate(product);
        productDAO.edit(product, "id");
    }

    public void deleteProduct(int id){
        productDAO.delete(id, "id");
    }

    public List<Product> viewAllProducts(){
        return productDAO.viewAll();
    }

}
