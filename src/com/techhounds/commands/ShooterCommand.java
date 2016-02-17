package com.techhounds.commands;

import com.techhounds.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterCommand extends Command {
	
	private ShooterSubsystem shoot;
	private double power;
	
    public ShooterCommand(double set) {
    	shoot = ShooterSubsystem.getInstance();
    	requires(shoot);
    	power = set;
    }
    
    public ShooterCommand(){
    	this(0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	shoot.setPower(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }
    // Called once after isFinished returns true
    protected void end() {
    	shoot.stopPower();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
