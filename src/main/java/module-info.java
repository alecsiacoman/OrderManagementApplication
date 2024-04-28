module com.example.pt2024_30423_coman_alecsia_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pt2024_30423_coman_alecsia_assignment_3 to javafx.fxml;
    exports com.example.pt2024_30423_coman_alecsia_assignment_3;
}