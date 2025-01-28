package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface AlgaeRollerbarIO {
    @AutoLog 
    public class PivotIOInputs{
        public double position = 0;
        public double voltage = 0;
        public double velocity = 0;
       
    }
    public default void setVoltage() {

    }
    //you need to make a class inside your interface
    //update inputs, and a setvoltage
    public default void updateInputs(){


    }
}
