package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Pivot extends SubsystemBase {
  private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
  private PivotIO io;
  private static Pivot instance;
  private double voltage = 0;

  public Pivot(PivotIO somethingElse) {
    this.io = somethingElse;
    io.updateInputs(inputs);
  }

  public static Pivot getInstance() {
    return instance;
  }

  public static Pivot initialize(PivotIO something) {
    if (instance == null) {
      //what do we do if instance is null
    }
    return instance;
  }

  public void setVoltage(double volts) {
    //fill in set voltage method
  }

  @Override
  public void periodic() {
    //fill in the periodic with the proper code to do logging
  }
}
