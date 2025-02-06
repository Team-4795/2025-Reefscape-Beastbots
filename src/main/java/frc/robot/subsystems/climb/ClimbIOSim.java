package frc.robot.subsystems.climb;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ClimbIOSim implements ClimbIO {
  DCMotorSim motor =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(
              DCMotor.getNEO(1), ClimbConstants.moi, ClimbConstants.gearing),
          DCMotor.getNEO(1));
  private double appliedVolts = 0.0;

  @Override
  public void setClimbVoltage(double volts) {
    appliedVolts = volts;
    motor.setInputVoltage(appliedVolts);
  }

  @Override
  public void updateInputs(ClimbIOInputs inputs) {
    inputs.velocityRPM = motor.getAngularVelocityRPM();
    inputs.voltage = motor.getInputVoltage();
    inputs.current = motor.getCurrentDrawAmps();
    motor.update(0.02);
  }
}
