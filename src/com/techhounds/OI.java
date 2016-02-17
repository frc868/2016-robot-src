package com.techhounds;

import com.techhounds.commands.*;
import com.techhounds.lib.hid.Button;
import com.techhounds.lib.hid.ControllerMap;
import com.techhounds.lib.hid.DPadButton;
import com.techhounds.lib.hid.JoystickButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
    
	private static OI instance;
	private Joystick joy;
	
	private ControllerMap driver;
	private ControllerMap operator;
	
	// DRIVER & OPERATOR CONTROLLER CHOOSER
	private SendableChooser driverChooser;
	private SendableChooser operatorChooser;
	
	// DRIVER CONTROLS
	int driverTestCommando = ControllerMap.Key.RT;
	int driverTestCommandoTwo = DPadButton.Direction.UP;
	
	// OPERATOR CONTROLS
	
	private OI() {
		
		joy = new Joystick(ControllerMap.Key.PORT_NUM);
		driverChooser = createControllerChooser();
		operatorChooser = createControllerChooser();

		SmartDashboard.putData("Driver Controller Chooser", driverChooser);
		SmartDashboard.putData("Operator Controller Chooser", operatorChooser);
		
		driver = new ControllerMap(new Joystick(0), (ControllerMap.Type) driverChooser.getSelected());
		operator = new ControllerMap(new Joystick(1), (ControllerMap.Type) operatorChooser.getSelected());
		
		initJoystickButtons();
	
		setup();
	}
	
	Button startCollector, stopCollector, angleUp, angleDown, upShooterSpeed, downShooterSpeed, stopShooter, startShooter;
	
	public void initJoystickButtons(){
		startCollector 		= new JoystickButton(joy, ControllerMap.Key.RT);
		stopCollector 		= new JoystickButton(joy, ControllerMap.Key.RB);
		angleUp				= new JoystickButton(joy, ControllerMap.Key.LT);
		angleDown			= new JoystickButton(joy, ControllerMap.Key.LB);
		upShooterSpeed		= new JoystickButton(joy, ControllerMap.Key.X);
		downShooterSpeed	= new JoystickButton(joy, ControllerMap.Key.Y);
		stopShooter			= new JoystickButton(joy, ControllerMap.Key.B);
		startShooter		= new JoystickButton(joy, ControllerMap.Key.A);
		
		startCollector.whenPressed(new CollectorCommand(.5));
		stopCollector.whenPressed(new CollectorCommand());
		angleUp.whenPressed(new AnglerCommand(.95));
		angleDown.whenPressed(new AnglerCommand(.05));
		upShooterSpeed.whenPressed(new ChangeShooterSpeedCommand(.3));
		downShooterSpeed.whenPressed(new ChangeShooterSpeedCommand(-.3));
		stopShooter.whenPressed(new ShooterCommand());
		startShooter.whenPressed(new ShooterCommand(.5));
	}
	
	private void setup() {
		if(Robot.oneControllerMode) {
			setupController();
		} else {
			setupDriver();
			setupOperator();
		}

		setupSmartDashboard();
	}
	
	/**
	 * @return Gets the only instance of the OI
	 */
	public static OI getInstance() {
		return instance == null ? instance = new OI(): instance;
	}
	
	/**
	 * Gets the Driver Controller Ready with its Buttons
	 */
	public void setupDriver() {
		
		// SAMPLE USAGE
		driver.getButton(driverTestCommando)
			.whenPressed(new Commando(), new Commando())
			.whenReleased(new Commando())
			.whileHeld(new Commando(), new Commando(), new Commando());
		
		driver.getButton(driverTestCommandoTwo)
			.whenPressed(new Commando())
			.whenReleased(); // It Will Just do A 0 Second Wait Command
	}
	
	/**
	 * Gets the Operator Controller Ready with its Buttons
	 */
	public void setupOperator() {
		// TODO: Add Operator Controls
	}
	
	/**
	 * Setup Single Controller Control
	 */
	public void setupController() {

		// SAMPLE USAGE
		driver.getButton(driverTestCommando)
			.whenPressed(new Commando(), new Commando())
			.whenReleased(new Commando())
			.whileHeld(new Commando(), new Commando(), new Commando());
		
		driver.getButton(driverTestCommandoTwo)
			.whenPressed(new Commando())
			.whenReleased(); // It Will Just do A 0 Second Wait Command
	}

	/**
	 * Gets the Smart Dashboard Ready with Commands (Act as Buttons)
	 */
	public void setupSmartDashboard() {
		SmartDashboard.putData("Update Controllers", new UpdateController());
	}
	
	/**
	 * Gets the Driver's Gamepad
	 */
	public ControllerMap getDriver() {
		return driver;
	}
	
	/**
	 * Gets the Operator's Gamepad
	 */
	public ControllerMap getOperator() {
		return operator;
	}
	
	/**
	 * Create Chooser for Controllers
	 */
	private SendableChooser createControllerChooser() {
		SendableChooser chooser = new SendableChooser();
		chooser.addDefault("Logitech", ControllerMap.Type.LOGITECH);
		chooser.addObject("XBOX ONE", ControllerMap.Type.XBOX_ONE);
		chooser.addObject("XBOX 360", ControllerMap.Type.XBOX_360);
		chooser.addObject("Playstation 4", ControllerMap.Type.PS4);
		return chooser;
	}
	
	/** 
	 * Update Controllers
	 */
	public void updateControllers() {
		driver.setControllerType((ControllerMap.Type) driverChooser.getSelected());
		operator.setControllerType((ControllerMap.Type) operatorChooser.getSelected());
		
		setup();
	}
	
	/** 
	 * Update dashboard when Called
	 */
	public void updateDashboard() {
		SmartDashboard.putString("Driver Type", getControllerString(driver));
		SmartDashboard.putString("Operator Type", getControllerString(operator));
	}
	
	public String getControllerString(ControllerMap joystick) {
		switch(joystick.getType()) {
			case PS4:
				return "PS4";
			case XBOX_360:
				return "XBOX_360";
			case XBOX_ONE:
				return "XBOX_ONE";
			case LOGITECH:
				return "LOGITECH";
			default:
				return "";
			
		}
	}
	public double getRight(){
		return driver.getRightPower();
	}
	public double getLeft(){
		return driver.getLeftPower();
	}
}