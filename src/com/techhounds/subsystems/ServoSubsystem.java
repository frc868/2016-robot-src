package com.techhounds.subsystems;

import com.techhounds.Robot;
import com.techhounds.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class ServoSubsystem extends Subsystem {
    
	private Servo servo;
	private double min, max;
	private boolean isMax;
	private static ServoSubsystem instance_winch_enable, instance_winch_lock,
		instance_scissor_one, instance_scissor_two;
	
	public ServoSubsystem(int port){
		this(port, 0, 1);
	}
	
	public ServoSubsystem(int port, double min, double max) {
		servo = new Servo(port);
		LiveWindow.addActuator("Servo_" + port, "Servo_" + port, servo);
		this.min = min;
		this.max = max;
	}
	
	public static ServoSubsystem getWinchEnable(){
		return instance_winch_enable == null ? instance_winch_enable = new ServoSubsystem(RobotMap.Servo.WINCH_ENABLE) : instance_winch_enable;
	}
	
	public static ServoSubsystem getWinchLock(){
		return instance_winch_lock == null ? instance_winch_lock = new ServoSubsystem(RobotMap.Servo.WINCH_LOCK) : instance_winch_lock;
	}
	
	public static ServoSubsystem getScissorOne(){
		return instance_scissor_one == null ? instance_scissor_one = new ServoSubsystem(RobotMap.Servo.SCISSOR_ONE) : instance_scissor_one;
	}
	
	public static ServoSubsystem getScissorTwo(){
		return instance_scissor_two == null ? instance_scissor_two = new ServoSubsystem(RobotMap.Servo.SCISSOR_TWO) : instance_scissor_two;
	}
	
	public void setPosition(double position) {
		servo.set(Robot.rangeCheck(position));
	}
	
	public void set(boolean position) {
		servo.set(Robot.rangeCheck(isMax ? min : max));
		isMax = position;
	}
	
	public double getPosition() {
		return servo.get();
	}
	
	public boolean getState() {
		return isMax;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
