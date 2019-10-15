/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TeleopDrive;

/** Add your docs here. */
public class Drivetrain extends Subsystem {
  private TalonSRX leftFront = new TalonSRX(RobotMap.DT_LEFT_FRONT);
  private TalonSRX leftBack = new TalonSRX(RobotMap.DT_LEFT_BACK);
  private TalonSRX rightFront = new TalonSRX(RobotMap.DT_RIGHT_FRONT);
  private TalonSRX rightBack = new TalonSRX(RobotMap.DT_RIGHT_BACK);

  public Drivetrain() {
    leftBack.follow(leftFront);
    rightBack.follow(rightFront);
  }

  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new TeleopDrive());
  }

  public void arcadeDrive(double speed, double turn) {
    leftFront.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, +turn);
    rightFront.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, -turn);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    leftFront.set(ControlMode.PercentOutput, leftSpeed);
    rightFront.set(ControlMode.PercentOutput, rightSpeed);
  }
}
