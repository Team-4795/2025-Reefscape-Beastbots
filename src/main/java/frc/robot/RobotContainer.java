// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Intake.IntakeIOReal;
import frc.robot.subsystems.Intake.IntakeIOSim;
import frc.robot.subsystems.climb.Climb;
import frc.robot.subsystems.climb.ClimbConstants;
import frc.robot.subsystems.climb.ClimbIOReal;
import frc.robot.subsystems.climb.ClimbIOSim;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIORedux;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOSpark;
import frc.robot.subsystems.goProServo.goProServo;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.pivot.PivotIOReal;
import frc.robot.subsystems.pivot.PivotIOSim;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  private final Drive drive;
  private final Intake intake;
  private final Climb climb;
  private final Pivot pivot;
  private final goProServo goProServo = new goProServo();

  // Controller
  private final CommandXboxController driverController = new CommandXboxController(0);
  private final CommandXboxController operatorController = new CommandXboxController(1);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(
                new GyroIORedux(),
                new ModuleIOSpark(0),
                new ModuleIOSpark(1),
                new ModuleIOSpark(2),
                new ModuleIOSpark(3));

        intake = Intake.initialize(new IntakeIOReal());
        climb = Climb.init(new ClimbIOReal());
        pivot = Pivot.initialize(new PivotIOReal());
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim());
        intake = Intake.initialize(new IntakeIOSim());
        climb = Climb.init(new ClimbIOSim());
        pivot = Pivot.initialize(new PivotIOSim());
        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        intake = Intake.initialize(new IntakeIOSim());
        climb = Climb.init(new ClimbIOSim());
        pivot = Pivot.initialize(new PivotIOSim());
        break;
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

    // Set up SysId routines
    autoChooser.addOption(
        "Drive Wheel Radius Characterization", DriveCommands.wheelRadiusCharacterization(drive));
    autoChooser.addOption(
        "Drive Simple FF Characterization", DriveCommands.feedforwardCharacterization(drive));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Forward)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Quasistatic Reverse)",
        drive.sysIdQuasistatic(SysIdRoutine.Direction.kReverse));
    autoChooser.addOption(
        "Drive SysId (Dynamic Forward)", drive.sysIdDynamic(SysIdRoutine.Direction.kForward));
    autoChooser.addOption(
        "Drive SysId (Dynamic Reverse)", drive.sysIdDynamic(SysIdRoutine.Direction.kReverse));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command, normal field-relative drive
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> driverController.getLeftY() * 0.8,
            () -> driverController.getLeftX() * 0.8,
            () -> driverController.getRightX() * -0.75));

    driverController.y().onTrue(Commands.runOnce(drive::zeroHeading, drive));

    operatorController
        .rightBumper()
        .whileTrue(
            Commands.startEnd(() -> climb.setVoltage(ClimbConstants.ForwardV), climb::stop, climb));
    operatorController
        .leftBumper()
        .whileTrue(
            Commands.startEnd(() -> climb.setVoltage(ClimbConstants.ReverseV), climb::stop, climb));

    // Lock to 0° when A button is held
    driverController
        .a()
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> -driverController.getLeftY(),
                () -> -driverController.getLeftX(),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    driverController.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Reset gyro to 0° when B button is pressed
    driverController
        .b()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));

    operatorController
        .b()
        .whileTrue(
            Commands.startEnd(() -> intake.setIntakeVoltage(3), () -> intake.setIntakeVoltage(0)));

    operatorController
        .a()
        .whileTrue(
            Commands.startEnd(() -> intake.setIntakeVoltage(-3), () -> intake.setIntakeVoltage(0)));

    operatorController
        .rightTrigger()
        .whileTrue(
            Commands.startEnd(
                () -> pivot.setVoltage(operatorController.getRightTriggerAxis() * 2),
                () -> pivot.setVoltage(0),
                pivot));

    operatorController
        .leftTrigger()
        .whileTrue(
            Commands.startEnd(
                () -> pivot.setVoltage(operatorController.getLeftTriggerAxis() * -2),
                () -> pivot.setVoltage(0),
                pivot));

    driverController
        .y()
        .onTrue(
            Commands.runOnce(
                () -> goProServo.setServoPosition(180)));

    driverController
        .x()
        .onTrue(
            Commands.runOnce(
                () -> goProServo.setServoPosition(0)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.get();
  }
}
