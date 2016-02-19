package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AnglerSubsystem extends Subsystem {
	
	private static AnglerSubsystem instance;
	private CANTalon angler;
	private double P = 0.008, I = 0, D = 0.01;
	private PIDController pid;
	private double TOLERANCE = 3;
    
	private AnglerSubsystem() {
		angler = new CANTalon(RobotMap.Collector.COLLECTOR_ANGLER);
		angler.setInverted(RobotMap.Angler.IS_INVERTED);
		LiveWindow.addActuator("Angler", "Motor", angler);
		angler.enableForwardSoftLimit(false);
		angler.enableReverseSoftLimit(false);
		angler.changeControlMode(TalonControlMode.PercentVbus);
		angler.configPotentiometerTurns(1);
		angler.setFeedbackDevice(CANTalon.FeedbackDevice.AnalogPot);
		
		//angler.setPID(P, I, D);
		pid = new PIDController(P, I, D, new PIDSource() {

			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}

			@Override
			public double pidGet() {
				return Robot.rangeCheck(angler.getAnalogInRaw(), 155, 500);
			}
			
		}, new PIDOutput() {

			@Override
			public void pidWrite(double output) {
				angler.set(output);
			}
			
		});
		
		pid.setOutputRange(-.35, .35);
		pid.setInputRange(100, 500);
		pid.setAbsoluteTolerance(TOLERANCE);
		//pid.enable();
		SmartDashboard.putData("Angler PID", pid);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public void setPosition(double position) {
		//angler.changeControlMode(TalonControlMode.Position);
		P = SmartDashboard.getNumber("Angler_P", P);
		I = SmartDashboard.getNumber("Angler_I", I);
		D = SmartDashboard.getNumber("Angler_D", D);
		pid.setPID(P, I, D);
		pid.setSetpoint(Robot.rangeCheck(position, 155, 500)); //0.575 is greatest position ever (only @ portcullis)
		pid.enable();
	}
	public double getSetPoint(){
		return pid.getSetpoint();
	}
	public boolean onTarget(){
		return pid.onTarget();
	}
	public void stopPower(){
		pid.disable();
		angler.set(0);
	}
	
	public void disableControl() {
		pid.disable();
	}
	
	public void setPower(double pow){
		//angler.changeControlMode(TalonControlMode.PercentVbus);
		//angler.set(Robot.rangeCheck(pow));
	}
	
	public double getPosition(){
		return angler.getAnalogInRaw();
		//return angler.get();
	}
	
	public boolean reachedTarget(double tolerance){
		return Math.abs(pid.getError()) < tolerance;
	}
	public double getError(){
		return pid.getError();
	}
	public void updateSmartDashboard(){
		SmartDashboard.putNumber("Angler_Position", getPosition());
		SmartDashboard.putNumber("Error: ", pid.getError());
		//SmartDashboard.putNumber("Angler: ", angler.getPosition());
	}
	
	public static AnglerSubsystem getInstance(){
		if(instance == null){
			instance = new AnglerSubsystem();
		}
		return instance;
	}

    public void initDefaultCommand() {
    	
    }
}

