package frc.robot.subsystems.pivot;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class PivotIOSim implements PivotIO {
  private SingleJointedArmSim pivotMotorSim =
      new SingleJointedArmSim(
          LinearSystemId.createSingleJointedArmSystem(DCMotor.getNEO(1), 2, 1),
          DCMotor.getNEO(1),
          1,
          1,
          0.5,
          3,
          false,
          0.5,
          0);

  @Override
  public void setVoltage(double voltage) {
    pivotMotorSim.setInputVoltage(voltage);
  }

  @Override
  public void updateInputs(PivotIOInputs inputs) {
    inputs.voltage = pivotMotorSim.getInput(1);
    inputs.velocity = pivotMotorSim.getVelocityRadPerSec();
    inputs.position = pivotMotorSim.getAngleRads();
  }
}
