package ucl.cs.camera;

public class Camera implements WriteListener {

  private Sensor sensor;
    private MemoryCard memoryCard;
    private boolean power;
    private boolean writing;

  public Camera(Sensor sensor, MemoryCard memoryCard) {
      this.sensor = sensor;
      this.memoryCard = memoryCard;
  }

  public void pressShutter() {
      if (power){
          byte[] data = sensor.readData();
          memoryCard.write(data);
          writing = true;
      }
  }

  public void powerOn() {
      sensor.powerUp();
      power = true;
  }

  public void powerOff() {
      if(!writing){
          sensor.powerDown();
          power = false;
      }
  }

    @Override
    public void writeComplete() {
        writing = false;
    }
}

