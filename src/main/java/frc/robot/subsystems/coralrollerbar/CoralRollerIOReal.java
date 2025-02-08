package frc.robot.subsystems.coralrollerbar;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.units.measure.Velocity;

public class CoralRollerIOReal implements CoralRollerIO {
  private final SparkMax CoralRollerMotor = new SparkMax(0, MotorType.kBrushless);

  public CoralRollerIOReal() {
    SparkMaxConfig coralRollerConfig = new SparkMaxConfig();
    coralRollerConfig.smartCurrentLimit(CoralRollerConstants.currentLimit);
    coralRollerConfig.voltageCompensation(12);
  }

  @Override
  public void setCoralRollerVoltage(double volts) {
    CoralRollerMotor.setVoltage(volts);
  }
  @Override
  public void updateInputs(CoralRollerIOInputs inputs){
    inputs.voltage = CoralRollerMotor.getBusVoltage() * CoralRollerMotor.getAppliedOutput();
    inputs.velocityRPM = CoralRollerMotor.getEncoder().getVelocity();
    inputs.current = CoralRollerMotor.getOutputCurrent();
  } 
}