package frc.robot.subsystems.intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class AlgaeRollerbarIOReal implements AlgaeRollerbarIO{
    private final SparkMax intakeMotor = new SparkMax(2,MotorType.kBrushed);//some numbers ig
    private final SparkMaxConfig config = new SparkMaxConfig();

    public AlgaeRollerbarIOReal(){
        //set the idlemode to coast and then pass in current limit
        //set idlemode?
        config.IdleMode kCoast;


    }



    public void AlgaeStop(){
        intakeMotor.stopMotor();
    }
}
