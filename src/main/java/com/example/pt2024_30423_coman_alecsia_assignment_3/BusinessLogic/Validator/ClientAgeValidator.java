package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator;

import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;

public class ClientAgeValidator implements Validator<Client>{
    private static final int MIN_AGE = 10;
    private static final int MAX_AGE = 100;

    @Override
    public void validate(Client client){
        if(client.getAge() < MIN_AGE || client.getAge() > MAX_AGE)
            throw new IllegalArgumentException("The client's age limit is not respected!");
    }
}
