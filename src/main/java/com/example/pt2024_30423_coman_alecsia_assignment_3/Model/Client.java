package com.example.pt2024_30423_coman_alecsia_assignment_3.Model;

public class Client {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Client(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString(){
        return "Client [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
