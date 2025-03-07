package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.climb.Climb;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.pivot.Pivot;

public class AutoCommands {
  private static final Climb climb = Climb.getInstance();
  private static final Pivot pivot = Pivot.getInstance();
  private static final Intake intake = Intake.getInstance();
  private static final Drive drive = Drive.getInstance();

  public static Command intake(double speed) {

    return Commands.sequence(Commands.run(()-> intake.setIntakeVoltage(speed*12)),
    Commands.waitSeconds(5),
    Commands.run(()-> intake.setIntakeVoltage(0)));
  }

  public static Command pivot(double speed) {
      return Commands.sequence(Commands.run(() -> pivot.setVoltage(speed * 12)),
       Commands.waitSeconds(5),
       Commands.run(() -> pivot.setVoltage(0)));
  }
}
