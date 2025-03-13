package frc.robot.subsystems.vision;


import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;

public class Vision extends SubsystemBase {
    
    private double camX;
    private double camY;
    private double camA;
    private double[] pose;
    private Rotation3d rotation;
    private LimelightHelpers.LimelightResults results;
    private int tagId;

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

    // align to nearest tag (hopefully)
    public double getDist(){
        double targetOffsetRad = LimelightHelpers.getTY("")*(Math.PI/180);
        double camTargetVOffset = VisionConstants.reefIdHeightIn - VisionConstants.camHeightIn;
        return (camTargetVOffset) / Math.tan(targetOffsetRad);
    }

    // may have to rewrite
    public void align(Drive drive){
        double distance = getDist();
        double hAngleError = LimelightHelpers.getTX("")*Math.PI/180;
        double rotation = hAngleError * VisionConstants.rotationK;
        DriveCommands.joystickDriveAtAngle(
                drive,
                () -> distance,
                () -> hAngleError,
                () -> new Rotation2d(rotation));
                // how to separate rotational offset from horizontal offset?
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
