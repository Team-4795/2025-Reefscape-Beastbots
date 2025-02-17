package frc.robot.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  // createvariable such as Io look at intake.java on mainbot adapt for your rollerbar, periodic
  // method update the sim and make sure your io is setting speed and stuff like that
  // After, go to robot container, initialize subsystem and do button bindings and simulate
  private IntakeIO io;
  private AlgaeRollerbarIOInputsAutoLogged inputs = new AlgaeRollerbarIOInputsAutoLogged();
  private double Voltage = 0.0;

  private static Intake instance;

  public static Intake getInstance() {
    return instance;
  }

  public static Intake initialize(IntakeIO io) {
    if (instance == null) {
      instance = new Intake(io);
    }
    return instance;
  }

  public Intake(IntakeIO io) {
    this.io = io;
    io.updateInputs(inputs);
  }

  public void setIntakeVoltage(double voltage) {
    Voltage = voltage;
    io.setVoltage(voltage);
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("AlgaeRollerbar", inputs);
    io.setVoltage(Voltage);
    Logger.recordOutput("AlgaeRollerbar/AlgaeRollerbar voltage", Voltage);
  }
}
