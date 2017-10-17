package ucl.cs.camera;

import jdk.internal.util.xml.impl.Input;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class CameraTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();


  Sensor sensor = context.mock(Sensor.class);
  MemoryCard memoryCard = context.mock(MemoryCard.class);
  Camera camera = new Camera(sensor,memoryCard);


  byte[] data;

  @Test
  public void switchingTheCameraOnPowersUpTheSensor() {
      context.checking(new Expectations(){{
          exactly(1).of(sensor).powerUp();
      }});
      camera.powerOn();

  }

    @Test
    public void switchingTheCameraOfPowersDownTheSensor() {
        context.checking(new Expectations(){{
            exactly(1).of(sensor).powerDown();
        }});
        camera.powerOff();

    }

    @Test
    public void pressingTheShutterWhilePowerOffDoesNothing(){
        context.checking(new Expectations(){{
            exactly(1).of(sensor).powerDown();
            exactly(0).of(sensor).readData();
        }});
        camera.powerOff();
        camera.pressShutter();
    }

    @Test
    public void pressingTheShutterWhilePowerOnCopiesData(){
        context.checking(new Expectations(){{
            exactly(1).of(sensor).powerUp();
            exactly(1).of(sensor).readData(); will(returnValue(data));
            exactly(1).of(memoryCard).write(data);
        }});
        camera.powerOn();
        camera.pressShutter();
    }

    @Test
    public void poweringOffWhileDataIsWritingDoesNothing(){
        context.checking(new Expectations(){{
            exactly(1).of(sensor).powerUp();
            exactly(1).of(sensor).readData(); will(returnValue(data));
            exactly(1).of(memoryCard).write(data);
            never(sensor).powerDown();
        }});
        camera.powerOn();
        camera.pressShutter();
        camera.powerOff();
    }

    @Test
    public void poweringOffWhileDataIsDoneWritingPowerOffsCamera(){
        context.checking(new Expectations(){{
            exactly(1).of(sensor).powerUp();
            exactly(1).of(sensor).readData(); will(returnValue(data));
            exactly(1).of(memoryCard).write(data);
            exactly(1).of(sensor).powerDown();
        }});
        camera.powerOn();
        camera.pressShutter();
        camera.powerOff();
        camera.writeComplete();
        camera.powerOff();
    }



}
