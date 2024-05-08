package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic;

import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator.EmailValidator;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator.PhoneValidator;
import com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator.Validator;
import com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess.ClientDAO;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL(){
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new PhoneValidator());

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
        for(Validator<Client> item : validators)
            item.validate(client);
        clientDAO.insert(client);
    }

    public void editClient(Client client){
        for(Validator<Client> item : validators)
            item.validate(client);
        clientDAO.edit(client, "id");
    }

    public void deleteClient(int id){
        clientDAO.delete(id, "id");
    }

    public List<Client> viewAllClients(){
        return clientDAO.viewAll();
    }

}
