package frc.robot.subsystems.intake;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


public class AlgaeRollerbarIOReal implements AlgaeRollerbarIO{
    private final SparkMax intakeMotor = new SparkMax(AlgaeRollerbarConstants.DEVICEID,MotorType.kBrushed);//some numbers ig
    private final RelativeEncoder encoder = intakeMotor.getEncoder();
    private final SparkMaxConfig config = new SparkMaxConfig();
    private double appliedVolts = 0;

    public AlgaeRollerbarIOReal(){
        //set the idlemode to coast and then pass in current limit
        //set idlemode?
        //make a file called algaerollerbar constants for can id and type shit 
        //in sparkmax there is method that lets you configure the motor, use that to configure it
        //clear stickyfaults on the motor
        //
        config.idleMode(IdleMode.kCoast);
        config.smartCurrentLimit(AlgaeRollerbarConstants.STALLLIMIT);
        intakeMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        intakeMotor.clearFaults();

    }
    @Override 
    public void setVoltage(double voltage){
        appliedVolts = voltage;
        intakeMotor.setVoltage(voltage);
    }
    @Override 
    public void updateInputs(AlgaeRollerbarIOInputs inputs){
        inputs.position = encoder.getPosition();
        inputs.velocity = encoder.getVelocity();
        inputs.voltage = appliedVolts;
    }

//override your io methods

   
}
