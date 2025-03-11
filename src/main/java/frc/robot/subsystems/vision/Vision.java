package frc.robot.subsystems.vision;


import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class Vision extends SubsystemBase {
    
    private double camX;
    private double camY;
    private double camA;
    private double[] pose;
    private Rotation3d rotation;
    private LimelightHelpers.LimelightResults results;

    private static Vision instance;

    public Vision(){
        rotation = new Rotation3d();
    }

    public static Vision getInstance(){
        if (instance == null){
            instance = new Vision();
        }
        return instance;
    }

    @Override
    public void periodic(){
        results = LimelightHelpers.getLatestResults("");
        camX = LimelightHelpers.getTX("");
        camY = LimelightHelpers.getTY("");
        camA = LimelightHelpers.getTA("");

        Logger.recordOutput("X displacement", camX);
        Logger.recordOutput("y displacement", camA);
        Logger.recordOutput("area", camA);
    }
}
