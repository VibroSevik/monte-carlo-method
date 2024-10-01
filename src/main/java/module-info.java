module com.oryreq.montecarlomethod {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;

    opens com.oryreq.montecarlomethod.models to javafx.base;
    opens com.oryreq.montecarlomethod to javafx.fxml;
    exports com.oryreq.montecarlomethod;
}