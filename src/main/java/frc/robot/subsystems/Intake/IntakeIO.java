package frc.robot.subsystems.Intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  // Instructins : make a main class algaerollerbar.java. make algaerollerbario sim
  public class AlgaeRollerbarIOInputs {
    public double position = 0;
    public double voltage = 0;
    public double velocity = 0;
  }

  public default void setVoltage(double voltage) {}

  // you need to make a class inside your interface
  // update inputs, and a setvoltage
  public default void updateInputs(AlgaeRollerbarIOInputs inputs) {}
}
