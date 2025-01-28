package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {
  @AutoLog
  public static class PivotIOInputs {
    public double position = 0;
    public double velocity = 0;
    public double voltage = 0;
  }

  public default void setVoltage(double voltage) {}
  ;

  public default void updateInputs(PivotIOInputs inputs) {}
  ;
}
