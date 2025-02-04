package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
  private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
  private static PivotIO io;
  private static Pivot instance;

  public Pivot(PivotIO somethingElse) {
    this.io = somethingElse;
    io.updateInputs(inputs);
  }

  public static Pivot getInstance() {
    return instance;
  }

  public static Pivot initialize(PivotIO something) {
    if (instance == null) {
      instance = new Pivot(something);
    }
    return instance;
  }

  public void setVoltage(double voltage) {
    io.setVoltage(voltage);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
  }
}
