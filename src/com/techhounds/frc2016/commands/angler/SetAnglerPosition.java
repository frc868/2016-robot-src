package com.techhounds.frc2016.commands.angler;

import com.techhounds.frc2016.subsystems.Angler;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class SetAnglerPosition extends Command {
	
	private Angler angle;
	private Double position;
	private Double timeout;

    public SetAnglerPosition(Double position) {
    	angle = Angler.getInstance();
    	requires(angle);
    	this.position = position;
    	this.timeout = null;
    }
    
    public SetAnglerPosition(Double position, Double timeout) {
    	angle = Angler.getInstance();
    	requires(angle);
    	this.position = position;
    	this.timeout = timeout;
    }
    
    public SetAnglerPosition(){
    	this(null);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//position = SmartDashboard.getNumber("Angler Set Height");
    	if(position == null)
    		angle.setPosition(angle.getRawPosition());
    	else
    		angle.setPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(timeout != null) {
    		if(timeout < timeSinceInitialized()) {
    			return true;
    		}
    	}
    	return angle.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
