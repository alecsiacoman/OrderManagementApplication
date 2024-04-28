package com.example.pt2024_30423_coman_alecsia_assignment_3.Model;

public class Client {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private int age;

    public Client(String name, String email, String phone, String address, int age){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }

    @Override
    public String toString(){
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", age=" + age + "]";
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age > 10)
            this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
