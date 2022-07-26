module com.zereb.doomlauncher {
    requires javafx.controls;
    requires com.google.gson;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;

    exports com.zereb.doomlauncher;
    opens com.zereb.doomlauncher.models to com.google.gson;
}