package com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic.Validator;

import com.example.pt2024_30423_coman_alecsia_assignment_3.AlertUtils;
import com.example.pt2024_30423_coman_alecsia_assignment_3.Model.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements Validator<Client>{
    @Override
    public void validate(Client client) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(client.getPhone());
        if(!matcher.matches()) {
            AlertUtils.showAlert("The phone number is not valid! It should contain only numbers and have exactly 10 characters.");
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
