/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  private Spark leftMotor;
  private Spark rightMotor;
  private SpeedControllerGroup motors;
  private DoubleSolenoid grabberPiston;
  private DoubleSolenoid pusherPiston;

  public Intake() {
    leftMotor = new Spark(RobotMap.INTAKE_MOTOR_LEFT);
    rightMotor = new Spark(RobotMap.INTAKE_MOTOR_RIGHT);

    rightMotor.setInverted(true);

    motors = new SpeedControllerGroup(leftMotor, rightMotor);

    grabberPiston = new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_GRABBER_1, RobotMap.INTAKE_SOLENOID_GRABBER_2);
    pusherPiston = new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_PUSHER_1, RobotMap.INTAKE_SOLENOID_PUSHER_2);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void open() {
    grabberPiston.set(Value.kForward);
  }

  public void close() {
    grabberPiston.set(Value.kReverse);
  }

  public void toggleOpenClose() {
    if (grabberPiston.get() ==  Value.kForward) {
      grabberPiston.set(Value.kReverse);
    } else {
      grabberPiston.set(Value.kForward);
    }
  }

  public void intake() {
    motors.set(1);
  }

  public void eject() {
    motors.set(-1);
  }

  public void push() {
    pusherPiston.set(Value.kForward);
  }

  public void retract() {
    pusherPiston.set(Value.kReverse);
  }
}
