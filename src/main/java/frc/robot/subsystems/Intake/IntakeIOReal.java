package frc.robot.subsystems.Intake;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class IntakeIOReal implements IntakeIO {
  //initialize a spark max motor and call it intakeMotor
  //make a relative encoder by acessing the relative encoder inside the sparkmax
  //make a sparkmax configurator
  private double appliedVolts = 0; //you'll use this to update your logged voltage value

  public IntakeIOReal() {
    //set current limit and set motor to coast mode
    //intakeMotor.clearFaults(); uncomment this later
  }
  // override your io methods and fill them in properly

}
