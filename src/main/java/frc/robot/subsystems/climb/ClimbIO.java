package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.AutoLog;

public interface ClimbIO {
  @AutoLog
  public static class ClimbIOInputs {
    public double current = 0.0;
    public double voltage = 0.0;
    public double velocityRPM = 0.0;
  }

  public default void updateInputs(ClimbIOInputs inputs) {}

  public default void setClimbVoltage(double volts) {}
}
