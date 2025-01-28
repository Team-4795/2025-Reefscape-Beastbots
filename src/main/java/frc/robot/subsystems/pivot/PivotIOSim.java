package frc.robot.subsystems.pivot;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class PivotIOSim implements PivotIO {
  private DCMotorSim pivotMotorSim =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 0.50, 0.1), DCMotor.getNEO(1), 1);

  @Override
  public void setVoltage(double voltage) {
    pivotMotorSim.setInputVoltage(voltage);
  }

  @Override
  public void updateInputs(PivotIOInputs inputs) {
    inputs.voltage = pivotMotorSim.getInputVoltage();
    inputs.velocity = pivotMotorSim.getAngularVelocityRPM();
    inputs.position = pivotMotorSim.getAngularPositionRotations();
  }
}
