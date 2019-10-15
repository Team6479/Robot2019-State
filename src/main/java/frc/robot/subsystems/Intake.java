/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  private VictorSPX leftMotor;
  private VictorSPX rightMotor;
  private DoubleSolenoid grabberPistonRight;
  private DoubleSolenoid grabberPistonLeft;
  private DoubleSolenoid pusherPiston;

  public Intake() {
    leftMotor = new VictorSPX(RobotMap.INTAKE_MOTOR_LEFT);
    rightMotor = new VictorSPX(RobotMap.INTAKE_MOTOR_RIGHT);

    rightMotor.configFactoryDefault();
    leftMotor.configFactoryDefault();

    rightMotor.follow(leftMotor);

    rightMotor.setInverted(true);

    rightMotor.setNeutralMode(NeutralMode.Brake);
    leftMotor.setNeutralMode(NeutralMode.Brake);

    grabberPistonRight = new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_GRABBER_RIGHT_1, RobotMap.INTAKE_SOLENOID_GRABBER_RIGHT_2);
    grabberPistonLeft = new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_GRABBER_LEFT_1, RobotMap.INTAKE_SOLENOID_GRABBER_LEFT_2);
    pusherPiston = new DoubleSolenoid(RobotMap.INTAKE_SOLENOID_PUSHER_1, RobotMap.INTAKE_SOLENOID_PUSHER_2);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void open() {
    grabberPistonRight.set(Value.kForward);
    grabberPistonLeft.set(Value.kForward);
  }

  public void close() {
    grabberPistonRight.set(Value.kReverse);
    grabberPistonLeft.set(Value.kReverse);
  }

  public void toggleOpenClose() {
    if (grabberPistonRight.get() == Value.kForward && grabberPistonLeft.get() == Value.kForward) {
      grabberPistonRight.set(Value.kReverse);
      grabberPistonLeft.set(Value.kReverse);
    } else {
      grabberPistonRight.set(Value.kForward);
      grabberPistonLeft.set(Value.kForward);
    }
  }

  public void intake() {
    leftMotor.set(ControlMode.PercentOutput, 1);
  }

  public void eject() {
    leftMotor.set(ControlMode.PercentOutput, -1);
  }

  public void push() {
    pusherPiston.set(Value.kForward);
  }

  public void retract() {
    pusherPiston.set(Value.kReverse);
  }
}
