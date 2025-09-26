module ui {
  requires core;
  requires persistency;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;

  opens ui to javafx.graphics, javafx.fxml;
}
