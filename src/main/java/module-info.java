module com.oryreq.montecarlomethod {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;
    requires java.compiler;
    requires com.google.common;
    requires java.desktop;

    opens com.oryreq.montecarlomethod.models to javafx.base;
    opens com.oryreq.montecarlomethod to javafx.fxml;

    exports com.oryreq.montecarlomethod;

    exports com.oryreq.montecarlomethod.windows.sidebar;
    opens com.oryreq.montecarlomethod.windows.sidebar to javafx.fxml;

    exports com.oryreq.montecarlomethod.windows.binomial_croupier.subwindows.input_data;
    opens com.oryreq.montecarlomethod.windows.binomial_croupier.subwindows.input_data to javafx.fxml;

    exports com.oryreq.montecarlomethod.windows.uniform_croupier.subwindows.input_data;
    opens com.oryreq.montecarlomethod.windows.uniform_croupier.subwindows.input_data to javafx.fxml;

    exports com.oryreq.montecarlomethod.windows.show_variations;
    opens com.oryreq.montecarlomethod.windows.show_variations to javafx.fxml;

    exports com.oryreq.montecarlomethod.windows;
    opens com.oryreq.montecarlomethod.windows to javafx.fxml;

    exports com.oryreq.montecarlomethod.windows.binomial_croupier;
    opens com.oryreq.montecarlomethod.windows.binomial_croupier to javafx.fxml;

    exports com.oryreq.montecarlomethod.windows.uniform_croupier to javafx.fxml;

    opens com.oryreq.montecarlomethod.windows.uniform_croupier to com.google.common, javafx.fxml;
}