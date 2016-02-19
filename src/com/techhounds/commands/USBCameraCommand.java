package com.techhounds.commands;

import com.techhounds.OI;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageInfo;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

/**
 *
 */
public class USBCameraCommand extends Command {
	private CameraServer camera;
	private boolean enabled;
	private static USBCamera ucam;
	
    public USBCameraCommand(boolean enb) {
        // Use requires() here to declare subsystem dependencies/
        // eg. requires(chassis);
    	camera = CameraServer.getInstance();
    	if(ucam == null){
    		ucam = new USBCamera("cam1");
    		//camera.startAutomaticCapture(ucam);
    		
    	}
    	
    	enabled = enb;
 
    	
    }
 
    // Called just before this Command runs the first time/
    @Override
    protected void initialize() {
    	
    	SmartDashboard.putBoolean("Camera Change To", enabled);
    	if(enabled){
    		ucam.startCapture();
    		//ucam2.stopCapture()

    	}
    	else{
    		//ucam2.startCapturer()
        	ucam.stopCapture();
        	

    	}
    	/*
    	SmartDashboard.putBoolean("Camera Enable", camera.isAutoCaptureStarted());
    	
    	enabled = !enabled;
    	if(enabled){
    		//ucam.startCapture();
    		camera.startAutomaticCapture("cam1");
    		SmartDashboard.putString("HERE3", "INITIALIZE2");
    	}
    	else{
    		ucam.stopCapture();
    		camera.startAutomaticCapture(ucam);
    	}
    	/*SmartDashboard.putString("HERE", "INITIALIZ");
    	if(!enabled){
    		System.out.println("HI2");
    		camera.startAutomaticCapture("cam1");
    		SmartDashboard.putString("HERE3", "INITIALIZE2");
    		
    	}
    	else{
    		camera.startAutomaticCapture("cam0");
    		SmartDashboard.putString("HERE", "INITIALZIE3");
    		
    		//OI.updateSmartDashboard().
    	}
    	camera.setQuality(50);
    	*/
    	
    	
    }
    

    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//SmartDashboard.putString("HERE", "EXECUTE")
		if(enabled){
			double start = Timer.getFPGATimestamp();
			Image frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
			ucam.getImage(frame);
			camera.setImage(frame);
			double time = Timer.getFPGATimestamp() - start;
			SmartDashboard.putNumber("Time: ", time);
		}
		SmartDashboard.putBoolean("cameraEnabled", enabled);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
