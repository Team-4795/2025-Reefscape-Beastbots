package frc.robot.commands;

import frc.robot.subsystems.pivot.Pivot;

import frc.robot.subsystems.climb.Climb;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Intake.Intake;

public class AutoCommands {
    private final static Climb climb = Climb.getInstance();
    private final static Pivot pivot = Pivot.getInstance();
    private final static  Intake intake = Intake.getInstance();

public static Command intake(double speed){
    return Commands.startEnd(() -> intake.setIntakeVoltage(speed * 12), () -> intake.setIntakeVoltage(0), intake);
}
public static Command pivot(double speed){
    return Commands.startEnd(()-> pivot.setVoltage(speed * 12), ()-> pivot.setVoltage(0), pivot);
}
}

