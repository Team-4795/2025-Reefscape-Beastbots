package frc.robot.subsystems.AlgaeRollerbar;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class AlgaeRollerbarIOSim implements AlgaeRollerbarIO {
  private double appliedVolts;
  private final DCMotorSim intakeMotor =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 0.5, 1), DCMotor.getNEO(1), 0, 0);

  @Override
  public void setVoltage(double voltage) {
    appliedVolts = voltage;
    intakeMotor.setInputVoltage(voltage);
  }

  @Override
  public void updateInputs(AlgaeRollerbarIOInputs inputs) {
    intakeMotor.update(0.02);
    inputs.position = intakeMotor.getAngularPositionRad();
    inputs.velocity = intakeMotor.getAngularVelocityRadPerSec();
    inputs.voltage = intakeMotor.getInputVoltage();
  }
}
