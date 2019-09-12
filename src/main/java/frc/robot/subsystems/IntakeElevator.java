/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.RobotState;

/**
 * Add your docs here.
 */
public class IntakeElevator extends Subsystem {
  private TalonSRX motor1 = new TalonSRX(RobotMap.INTAKE_ELEVATOR_MOTOR_1);
  private TalonSRX motor2 = new TalonSRX(RobotMap.INTAKE_ELEVATOR_MOTOR_2);
  private RobotState robotState;
  private int elevatorHeight;

  public IntakeElevator(RobotState robotState) {
    this.robotState = robotState;

    // Ensure talons dont keep any lingering settings
    motor1.configFactoryDefault();
    motor2.configFactoryDefault();

    // Set motor2 to follow motor1
    motor2.follow(motor1);

    // Set neutral mode
    motor1.setNeutralMode(NeutralMode.Brake);
    motor2.setNeutralMode(NeutralMode.Brake);

    // Configure encoder
    motor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

    // Set sensor phase
    motor1.setSensorPhase(true);
    motor2.setSensorPhase(true);

    // Configure deadband to be set to the minimum 0.1%.
    motor1.configNeutralDeadband(0.001);
    motor2.configNeutralDeadband(0.001);

    // Configure motion magic
    motor1.configMotionAcceleration(0);
    motor1.configMotionCruiseVelocity(0);

    // Configure max peak output
    motor1.configPeakOutputForward(1.0);
    motor1.configPeakOutputReverse(-1.0);
    motor2.configPeakOutputForward(1.0);
    motor2.configPeakOutputReverse(-1.0);

    // Config PID
    motor1.config_kP(0, 0);
    motor1.config_kI(0, 0);
    motor1.config_kD(0, 0);
    motor1.config_kF(0, 0);

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getHeight() {
    return robotState.getRobotState().elevatorHeights[elevatorHeight];
  }

  public void setHeight() {
    // motor1.set
  }

  public void incerementHeight() {
    int numHeights = robotState.getRobotState().elevatorHeights.length - 1;

    if (elevatorHeight > numHeights) {
      elevatorHeight = numHeights;
    }

    if (!(elevatorHeight + 1 > numHeights)) {
      elevatorHeight++;
    }
  }

  public void decrementHeight() {
    if (!(elevatorHeight - 1 < 0)) {
      elevatorHeight--;
    }
  }

  private void zeroSensors() {
    motor1.getSensorCollection().setQuadraturePosition(0, 0);
    motor2.getSensorCollection().setQuadraturePosition(0, 0);
  }
}
