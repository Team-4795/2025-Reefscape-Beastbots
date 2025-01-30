package frc.robot.subsystems.climb;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Climb extends SubsystemBase {
  private ClimbIO io;
  private final ClimbIOInputsAutoLogged inputs = new ClimbIOInputsAutoLogged();

  private double velocity = 0.0;
  private static Climb instance;

  private Climb(ClimbIO io) {
    this.io = io;
    io.updateInputs(inputs);
  }

  public void up(){
    io.setClimbVoltage(12);
  }

  public void down(){
    io.setClimbVoltage(-12);
  }

  public void stop(){
    io.setClimbVoltage(0);
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

  public void setVelocity(double v) {
    this.velocity = v;
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
    Logger.processInputs("climb", inputs);
    io.setClimbVoltage(MathUtil.clamp(velocity * 12, -12, 12));
  }
}
