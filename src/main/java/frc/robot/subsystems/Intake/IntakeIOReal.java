package frc.robot.subsystems.Intake;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class IntakeIOReal implements IntakeIO {
  private final SparkMax intakeMotor =
      new SparkMax(IntakeConstants.DEVICEID, MotorType.kBrushless); // some numbers ig
  private final RelativeEncoder encoder = intakeMotor.getEncoder();
  private final SparkMaxConfig config = new SparkMaxConfig();
  private double appliedVolts = 0;

  public IntakeIOReal() {
    config.idleMode(IdleMode.kCoast);
    config.smartCurrentLimit(IntakeConstants.STALLLIMIT);
    intakeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    intakeMotor.clearFaults();
  }

  @Override
  public void setVoltage(double voltage) {
    appliedVolts = voltage;
    intakeMotor.setVoltage(voltage);
  }

  @Override
  public void updateInputs(AlgaeRollerbarIOInputs inputs) {
    inputs.position = encoder.getPosition();
    inputs.velocity = encoder.getVelocity();
    inputs.voltage = intakeMotor.getAppliedOutput() * intakeMotor.getBusVoltage();
  }

  // override your io methods

}
