module org.example.newteam {
    requires javafx.controls;
    requires javafx.fxml;
    requires logging.jvm;
    requires net.devrieze.xmlutil.serialization;
    requires kotlin.result.jvm;
    requires org.jdbi.v3.core;
    requires org.jdbi.v3.kotlin;
    requires org.jdbi.v3.sqlobject;
    requires org.jdbi.v3.sqlobject.kotlin;
    requires com.github.benmanes.caffeine;
    requires kotlinx.serialization.json;


    opens org.example.newteam to javafx.fxml;
    exports org.example.newteam;
}