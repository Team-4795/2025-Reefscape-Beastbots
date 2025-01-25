package frc.robot.subsystems.coralrollerbar;

import org.littletonrobotics.junction.AutoLog;

public interface CoralRollerIO {
  @AutoLog
  public static class CoralRollerIOInputs {
    public double current = 0;
    public double voltage = 0;
    public double velocityRPM = 0;
  }

  public default void updateInputs(CoralRollerIOInputs inputs) {}

  public default void setDesiredState(double speed) {}

  public default void setCoralRollerVoltage(double volts) {}
}
