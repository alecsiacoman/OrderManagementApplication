package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic;

import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator.ClientValidator;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.ClientDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientDAO clientDAO;

    public ClientBLL(){
        clientDAO = new ClientDAO();
    }

    public Client findClientById(int id){
        Client client = ClientDAO.findById(id);
        if(client == null){
            throw new NoSuchElementException("The client with id = " + id + " doesn't exist!");
        }
        return client;
    }

    public void insertClient(Client client){
        new ClientValidator().validate(client);
        clientDAO.insert(client);
    }

    public void editClient(Client client){
        new ClientValidator().validate(client);
        clientDAO.edit(client, "id");
    }

    public void deleteClient(int id){
        clientDAO.delete(id, "id");
    }

    public List<Client> viewAllClients(){
        return clientDAO.viewAll();
    }

}
