module core {
  requires com.fasterxml.jackson.core;
  requires com.fasterxml.jackson.annotation;
  
  exports core;
  opens core to com.fasterxml.jackson.databind;
  exports core.enums;
}
