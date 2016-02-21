package com.techhounds.commands.auton;

import com.techhounds.RobotMap;
import com.techhounds.commands.DriveDistance;
import com.techhounds.commands.angler.SetAnglerPosition;
import com.techhounds.commands.gyro.RotateUsingGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class CrossCDF extends CommandGroup {
    
    public  CrossCDF() {
    	
    	//this command assumes the robot is facing forward in front of the defense
    	
    	addSequential(new RotateUsingGyro(180));//turns around
    	
    	addSequential(new DriveDistance(-1, .5));//drives up on to ramp to its position
    	
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_MIN_HEIGHT));//lowers collector onto defense
    	
    	addSequential(new WaitCommand(1));//waits until defense is lowered
    	
    	addParallel(new DriveDistance(-3, .7));//drives over defense and waits a moment before...
    	addSequential(new WaitCommand(.2));
    	
    	addSequential(new SetAnglerPosition(RobotMap.Collector.COLLECTOR_HEIGHT));//...raising collector to prevent damage to it
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}