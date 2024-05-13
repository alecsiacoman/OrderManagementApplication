package com.example.pt2024_30423_coman_alecsia_assignment_3.Model;

/**
 * Represents a client entity.
 */
public class Client {
    private int id;
    private String name;
    private String email;
    private String phone;

    /**
     * Constructs a new client with the given name, email, and phone number.
     * @param name The name of the client.
     * @param email The email of the client.
     * @param phone The phone number of the client.
     */
    public Client(String name, String email, String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Constructs a new client with the given ID, name, email, and phone number.
     * @param id The ID of the client.
     * @param name The name of the client.
     * @param email The email of the client.
     * @param phone The phone number of the client.
     */
    public Client(int id, String name, String email, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Returns a string representation of the client.
     * @return A string representation of the client.
     */
    @Override
    public String toString(){
        return "Client [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
    }

    /**
     * Gets the name of the client.
     * @return The name of the client.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the client.
     * @param name The new name of the client.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the phone number of the client.
     * @return The phone number of the client.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the client.
     * @param phone The new phone number of the client.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the ID of the client.
     * @return The ID of the client.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the client.
     * @param id The new ID of the client.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the email of the client.
     * @return The email of the client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the client.
     * @param email The new email of the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
