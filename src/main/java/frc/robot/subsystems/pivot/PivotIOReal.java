package frc.robot.subsystems.pivot;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

public class PivotIOReal implements PivotIO {
  private SparkMax pivotMotor = new SparkMax(PivotConstants.pivotMotorID, MotorType.kBrushless);
  private SparkMaxConfig config = new SparkMaxConfig();
  private AbsoluteEncoder encoder = pivotMotor.getAbsoluteEncoder();

  public PivotIOReal() {
    config.smartCurrentLimit(PivotConstants.stallLimit);
    pivotMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    pivotMotor.getStickyFaults();
  }

  @Override
  public void setVoltage(double voltage) {
    pivotMotor.setVoltage(voltage);
  }

  @Override
  public void updateInputs(PivotIOInputs inputs) {
    inputs.voltage = pivotMotor.getBusVoltage() * pivotMotor.getAppliedOutput();
    inputs.position = encoder.getPosition();
    inputs.velocity = encoder.getVelocity();
  }
}
