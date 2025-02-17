package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Climb extends SubsystemBase {
  private ClimbIO io;
  private final ClimbIOInputsAutoLogged inputs = new ClimbIOInputsAutoLogged();

  private double appliedVolts = 0.0;
  private static Climb instance;

  private Climb(ClimbIO io) {
    this.io = io;
    io.updateInputs(inputs);
  }

  public void setVoltage(double volts) {
    io.setClimbVoltage(volts);
    appliedVolts = volts;
  }

  public void stop() {
    io.setClimbVoltage(0);
    appliedVolts = 0;
  }

  public static Climb getInstance() {
    return instance;
  }

  public static Climb init(ClimbIO io) {
    if (instance == null) {
      instance = new Climb(io);
    }
    return instance;
  }

  // public void setVelocity(double v) {
  //   this.velocity = v;
  // }

  public double getVelocity() {
    return inputs.velocityRPM;
  }

  public double getCurrent() {
    return inputs.current;
  }

  public double getVoltage() {
    return inputs.voltage;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("climb", inputs);
    io.setClimbVoltage(appliedVolts);
  }
}
