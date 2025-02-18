package frc.robot.subsystems.goProServo;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class goProServo extends SubsystemBase {
  public static Servo goProServoa = new Servo(1);

  public void setServoPosition(int degrees) {
    goProServoa.setAngle(degrees);
  }
}
