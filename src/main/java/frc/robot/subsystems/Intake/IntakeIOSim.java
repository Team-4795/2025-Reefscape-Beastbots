package frc.robot.subsystems.Intake;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class IntakeIOSim implements IntakeIO {
  private double appliedVolts; //you'll use this to update voltage value
  private final DCMotorSim intakeMotor =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 0.5, 1), DCMotor.getNEO(1), 0, 0);

  //implement the io methods you made, note that you'll need to use the .update method in your simulated motor to do anything
}
