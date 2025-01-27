package frc.robot.subsystems.coralrollerbar;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class CoralRollerIOSim implements CoralRollerIO {
  DCMotorSim motor =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1), 0.5, 0.1),
          DCMotor.getKrakenX60(1),
          0.0,
          0.0);
  private double appliedVolts = 0;

  @Override
  public void setCoralRollerVoltage(double volts) {
    appliedVolts = volts;
    motor.setInputVoltage(appliedVolts);
  }

  @Override
  public void updateInputs(CoralRollerIOInputs inputs) {
    inputs.velocityRPM = motor.getAngularVelocityRPM();
    inputs.voltage = appliedVolts;
    inputs.current = motor.getCurrentDrawAmps();
    motor.update(0.02);
  }
}
