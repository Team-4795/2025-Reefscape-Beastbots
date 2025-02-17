package frc.robot.subsystems.pivot;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Pivot extends SubsystemBase {
  private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
  private ProfiledPIDController controller = new ProfiledPIDController(
    PivotConstants.kP, 
    PivotConstants.kI, 
    PivotConstants.kD, 
    new TrapezoidProfile.Constraints(PivotConstants.kS, PivotConstants.kA));
  private SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(PivotConstants.kS, PivotConstants.kV);
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
      instance = new Pivot(something);
    }
    return instance;
  }

  public void setVoltage(double volts) {
    io.setVoltage(volts);
    voltage = volts;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Pivot", inputs);
    io.setVoltage(voltage);
  }
}
