module knitspace_frontend {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;

  opens knitspace_frontend to javafx.graphics, javafx.fxml;
}
