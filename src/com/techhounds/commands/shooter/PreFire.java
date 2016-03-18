package com.techhounds.commands.shooter;

import com.techhounds.commands.SetRumble;
import com.techhounds.commands.collector.SetCollectorPower;
import com.techhounds.commands.collector.WaitForBeanBreak;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class PreFire extends CommandGroup {

	public PreFire() {

		addParallel(new SetCollectorPower(-.4, true));
		addSequential(new WaitForBeanBreak(false));
		//addSequential(new WaitCommand(1));
		//addParallel(new SetCollectorPower(.4, true));
		//addParallel(new WaitForBeanBreak(true));
		//addSequential(new WaitCommand(.01));
		addSequential(new SetCollectorPower(0, true));
		addSequential(new SetShooterSpeed(69));
		addSequential(new SetRumble(true, true));
	}
	
	public PreFire(boolean valu) {

		addParallel(new SetCollectorPower(-.2, true));
		addSequential(new WaitForBeanBreak(false));
		//addSequential(new WaitCommand(1));
		//addParallel(new SetCollectorPower(.4, true));
		//addParallel(new WaitForBeanBreak(true));
		//addSequential(new WaitCommand(.01));
		addSequential(new SetCollectorPower(0, true));
		addSequential(new SetShooterSpeed(69));
	}
}