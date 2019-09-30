/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.team6479.lib.controllers.CBXboxController.Buttons;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopIntakePivoter extends Command {
  private Button leftBumper;
  private Button rightBumper;


  public TeleopIntakePivoter() {
    requires(Robot.intakePivoter);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    leftBumper = Robot.oi.xbox.getButton(Buttons.kBumperLeft);
    rightBumper = Robot.oi.xbox.getButton(Buttons.kBumperRight);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(leftBumper.get()) {
      Robot.intakePivoter.incerementHeight();
    }
    else if(rightBumper.get()) {
      Robot.intakePivoter.decrementHeight();
    }


  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
