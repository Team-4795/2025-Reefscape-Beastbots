package frc.robot.subsystems.climb;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class ClimbIOReal implements ClimbIO {
  private SparkMax ClimbMotor = new SparkMax(ClimbConstants.CanID, MotorType.kBrushless);
  private SparkMaxConfig Config = new SparkMaxConfig();

  public ClimbIOReal() {
    Config.smartCurrentLimit(ClimbConstants.currentLimit);
    Config.idleMode(IdleMode.kBrake);
    ClimbMotor.configure(Config, null, null);
  }

  @Override
  public void setClimbVoltage(double volts) {
    ClimbMotor.setVoltage(volts);
  }

  @Override
  public void updateInputs(ClimbIOInputs inputs) {
    inputs.voltage = ClimbMotor.getBusVoltage() * ClimbMotor.getAppliedOutput();
    inputs.velocityRPM = ClimbMotor.getEncoder().getVelocity();
    inputs.current = ClimbMotor.getOutputCurrent();
  }
}
