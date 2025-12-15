package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  //create io object called io, instance, autologged class

  double Voltage; 

  //fill in get instance
  public static Intake getInstance() {
    return null;
  }

  //fill in the intialize method
  public static Intake initialize(IntakeIO io) {
    return null; 
  }

  //fill in the constructor
  public Intake(IntakeIO io) {
  }

  //use the method in io you made for setting voltage here + update the double in this class that stores voltage
  public void setIntakeVoltage(double voltage) {
  }

  @Override
  public void periodic() {
    //what do we want to run periodically 
  }
}
