package frc.robot.subsystems.pivot;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class PivotIOSim implements PivotIO {
  private double aaaaa = 0;
  private SingleJointedArmSim pivotMotorSim =
      new SingleJointedArmSim(
          LinearSystemId.createSingleJointedArmSystem(DCMotor.getNEO(1), 1, 0.5),
          DCMotor.getNEO(1),
          0.5,
          1,
          0,
          10,
          false,
          0);

  @Override
  public void setVoltage(double voltage) {
    //properly overide the set voltage method
  }

  @Override
  public void updateInputs(PivotIOInputs inputs) {
    pivotMotorSim.update(0.02);
    //update the logged values with data from simulator
  }
}
