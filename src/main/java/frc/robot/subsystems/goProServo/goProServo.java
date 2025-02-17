package frc.robot.subsystems.goProServo;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class goProServo extends SubsystemBase {
    public static Servo goProServo = new Servo(1);

    public static void setServoPosition (int degrees) {
        goProServo.setAngle(degrees);
    }
}
