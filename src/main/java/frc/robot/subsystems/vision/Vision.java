package frc.robot.subsystems.vision;


import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Quaternion;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;

public class Vision extends SubsystemBase {
    
    private double camX;
    private double camY;
    private double camA;
    private double dist;
    public double[] pose;
    public double[] loggedpose;
    private Rotation3d rotation;
    private LimelightHelpers.LimelightResults results;
    private int tagId;
    private boolean hasTargets;

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
        int numAprilTags = results.targets_Fiducials.length;
        
        camX = LimelightHelpers.getTX("");
        camY = LimelightHelpers.getTY("");
        camA = LimelightHelpers.getTA("");
        //camArea = LimelightHelpers.getTA("")
        pose = LimelightHelpers.getBotPose_wpiRed("");
        dist = LimelightHelpers.getTargetPose3d_CameraSpace("").getTranslation().getZ();
        hasTargets = LimelightHelpers.getTV("");

        rotation = new Rotation3d(pose[3]*Math.PI/180, pose[4]*Math.PI/180, pose[5]*Math.PI/180);
        Quaternion botQuaternion = rotation.getQuaternion();

        loggedpose[0] = pose[0];
        loggedpose[1] = pose[1];
        loggedpose[2] = pose[2];
        loggedpose[3] = botQuaternion.getW();
        loggedpose[4] = botQuaternion.getX()*3.14159/180;
        loggedpose[5] = botQuaternion.getY();
        loggedpose[6] = botQuaternion.getZ();

        Logger.recordOutput("Vision/Distance", dist);
        SmartDashboard.putBoolean("Vision Target?", hasTargets);
        SmartDashboard.putNumber("Target Area", camA);
        SmartDashboard.putNumber("Displacement Angle X", camX);
        SmartDashboard.putNumber("Displacement Angle Y", camY);
        SmartDashboard.putNumber("Number of AprilTags", numAprilTags);
        SmartDashboard.putNumberArray("Botpose", loggedpose);

        Logger.recordOutput("X displacement", camX);
        Logger.recordOutput("y displacement", camA);
        Logger.recordOutput("area", camA);
    }
}
