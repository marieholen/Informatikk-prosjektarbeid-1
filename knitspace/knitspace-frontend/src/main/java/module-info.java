module knitspace {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires java.desktop;

  requires java.net.http;

  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.annotation;

  opens knitspace to javafx.graphics, javafx.fxml;
  opens knitspace.model to com.fasterxml.jackson.databind;

  exports knitspace.enums to com.fasterxml.jackson.databind;
}
