package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public class PivotIOReal implements PivotIO {
  private SparkMax pivotMotor = new SparkMax(PivotConstants.pivotMotorID, MotorType.kBrushless);
  private TrapezoidProfile profile = new TrapezoidProfile(new TrapezoidProfile.Constraints(PivotConstants.kS, PivotConstants.kA));
  private TrapezoidProfile.State goal;
  private TrapezoidProfile.State setpoint;
  private ProfiledPIDController controller =
      new ProfiledPIDController(
          PivotConstants.kP,
          PivotConstants.kI,
          PivotConstants.kD,
          new TrapezoidProfile.Constraints(PivotConstants.maxV, PivotConstants.maxA));
  private ArmFeedforward feedForward =
      new ArmFeedforward(PivotConstants.kS, PivotConstants.kG, PivotConstants.kV, PivotConstants.kA);
  private SparkMaxConfig config = new SparkMaxConfig();
  private RelativeEncoder encoder = pivotMotor.getEncoder();

  public PivotIOReal() {
    config.smartCurrentLimit(PivotConstants.stallLimit);
    pivotMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    pivotMotor.getStickyFaults();

    goal = new TrapezoidProfile.State(getPosition(), 0);
    setpoint = new TrapezoidProfile.State(getPosition(), 0);

    config.encoder.positionConversionFactor(2 * Math.PI / 25.0);
    config.encoder.velocityConversionFactor(2 * Math.PI / 60.0 / 25.0);
  }

  @Override
  public void setVoltage(double voltage) {
    pivotMotor.setVoltage(voltage);
  }

  @Override
  public void setGoal(double angle){
    setpoint = new TrapezoidProfile.State(getPosition(), encoder.getVelocity());
    goal = new TrapezoidProfile.State(angle, 0);
  }

  @Override
  public double getPosition(){
    return encoder.getPosition() + PivotConstants.offset;
  }

  @Override
  public void updateInputs(PivotIOInputs inputs) {
    inputs.voltage = pivotMotor.getBusVoltage();
    inputs.position = encoder.getPosition();
    inputs.velocity = encoder.getVelocity();
  }

  @Override
  public void updateMotionProfile(){
    setpoint = profile.calculate(0.02, setpoint, goal);
    double ffVolts = feedForward.calculate(getPosition(), setpoint.velocity);
    Logger.recordOutput("pivot ff volts", ffVolts);
    double pidVolts = controller.calculate(getPosition(), setpoint.position);
    Logger.recordOutput("pivot pid volts", ffVolts);

    setVoltage(ffVolts + pidVolts);
    }
}
