module com.example.pt2024_30423_coman_alecsia_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pt2024_30423_coman_alecsia_assignment_3 to javafx.fxml;
    opens com.example.pt2024_30423_coman_alecsia_assignment_3.Model to javafx.fxml;
    opens com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic to javafx.fxml;
    opens com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess to javafx.fxml;
    opens com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation to javafx.fxml;

    exports com.example.pt2024_30423_coman_alecsia_assignment_3;
}