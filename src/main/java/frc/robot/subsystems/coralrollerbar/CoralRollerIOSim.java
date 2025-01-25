package frc.robot.subsystems.coralrollerbar;

import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class CoralRollerIOSim implements CoralRollerIO {
  DCMotorSim motor = new DCMotorSim(null, null, null);
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
  }
}
