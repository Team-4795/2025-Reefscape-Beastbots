package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AlgaeRollerbar extends SubsystemBase {
  // createvariable such as Io look at intake.java on mainbot adapt for your rollerbar, periodic
  // method update the sim and make sure your io is setting speed and stuff like that
  // After, go to robot container, initialize subsystem and do button bindings and simulate
  private AlgaeRollerbarIO io;
  private AlgaeRollerbarIOInputsAutoLogged inputs = new AlgaeRollerbarIOInputsAutoLogged();
  private double intakeVoltage = 0.0;

  private static AlgaeRollerbar instance;

  public static AlgaeRollerbar getInstance() {
    return instance;
  }

  public static AlgaeRollerbar initialize(AlgaeRollerbarIO io) {
    if (instance == null) {
      instance = new AlgaeRollerbar(io);
    }
    return instance;
  }

  private AlgaeRollerbar(AlgaeRollerbarIO io) {
    this.io = io;
    io.updateInputs(inputs);
  }

  public void setIntakeVoltage(double voltage) {
    intakeVoltage = voltage;
  }

  public Command intake() {
    return startEnd(() -> setIntakeVoltage(AlgaeRollerbarConstants.intake), () -> setIntakeVoltage(0));
    
  }
  public Command intakeSlow() {
    return startEnd(() -> setIntakeVoltage(AlgaeRollerbarConstants.slow), () -> setIntakeVoltage(0));
  }

  public Command reverse() {
    return startEnd(() -> setIntakeVoltage(AlgaeRollerbarConstants.reverse), () -> setIntakeVoltage(0));
  } 

  @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
        io.setVoltage(intakeVoltage);
        Logger.recordOutput("Intake/Intake speed", intakeVoltage);

    }
}
