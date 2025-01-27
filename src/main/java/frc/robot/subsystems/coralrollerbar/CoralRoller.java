package frc.robot.subsystems.coralrollerbar;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class CoralRoller extends SubsystemBase {
  private final CoralRollerIO io;
  private final CoralRollerIOInputsAutoLogged inputs = new CoralRollerIOInputsAutoLogged();

  private double velocity = 0;
  private static CoralRoller instance;

  public static CoralRoller getInstance() {
    return instance;
  }

  public static CoralRoller init(CoralRollerIO io) {
    if (instance == null) {
      instance = new CoralRoller(io);
    }
    return instance;
  }

  private CoralRoller(CoralRollerIO io) {
    this.io = io;
    io.updateInputs(inputs);
  }

  public void setVoltage(double v) {
    io.setCoralRollerVoltage(v);
  }

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
    Logger.processInputs("CoralRoller", inputs);
  }
}
