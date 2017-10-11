package ucl.cs.camera;

public interface Sensor {

  byte[] readData();

  void powerUp();

  void powerDown();
}
