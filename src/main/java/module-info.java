module com.example.pt2024_30423_coman_alecsia_assignment_3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;

    opens com.example.pt2024_30423_coman_alecsia_assignment_3.Presentation to javafx.fxml;
    opens com.example.pt2024_30423_coman_alecsia_assignment_3.BusinessLogic to javafx.base;
    opens com.example.pt2024_30423_coman_alecsia_assignment_3.DataAccess to javafx.base;
    opens com.example.pt2024_30423_coman_alecsia_assignment_3.Model to javafx.base;

    exports com.example.pt2024_30423_coman_alecsia_assignment_3;
}
