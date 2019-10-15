/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.Robot.IntakeState;
import frc.robot.Robot.PivotState;
import frc.robot.RobotMap;
import frc.robot.commands.TeleopIntakePivoter;

/**
 * Add your docs here.
 */
public class IntakePivoter extends Subsystem {
  private TalonSRX motor1 = new TalonSRX(RobotMap.INTAKE_PIVOTER_MOTOR_1);
  private TalonSRX motor2 = new TalonSRX(RobotMap.INTAKE_PIVOTER_MOTOR_2);
  private IntakeState robotState;
  private double[] elevatorHeights;
  private int elevatorHeightIndex;
  private boolean isInverted;

  public IntakePivoter() {
    isInverted = false;

    // Ensure talons dont keep any lingering settings
    motor1.configFactoryDefault();
    motor2.configFactoryDefault();

    // Set motor2 to follow motor1
    motor2.follow(motor1);

    // Set neutral mode
    motor1.setNeutralMode(NeutralMode.Brake);
    motor2.setNeutralMode(NeutralMode.Brake);

    // Configure encoder
    motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute , 0, 0);

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

    // Set hooks for Intake State changes
    Robot.intakeState.addStateHooks(IntakeState.BALL, new Runnable(){
      @Override
      public void run() {
        elevatorHeights = new double[] {0, 0, 0, 0};
      }
    });
    Robot.intakeState.addStateHooks(IntakeState.HATCH, new Runnable(){
      @Override
      public void run() {
        elevatorHeights = new double[] {0, 0, 0};
      }
    });

    // Set hooks for Pivot State changes
    Robot.pivotState.addStateHooks(PivotState.NORMAL, new Runnable(){
      @Override
      public void run() {
        setInverted(false);
      }
    });
    Robot.pivotState.addStateHooks(PivotState.INVERTED, new Runnable(){
      @Override
      public void run() {
        setInverted(true);
      }
    });

  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new TeleopIntakePivoter());
  }

  public void setInverted(boolean bool) {
    isInverted = bool;
  }

  public double getTargetHeight() {
    return elevatorHeights[elevatorHeightIndex];
  }

  public void setHeightMotionMagic(double height) {
    motor1.set(ControlMode.MotionMagic, height);
  }

  public void setHeightPercent(double percent) {
    motor1.set(ControlMode.PercentOutput, percent);
  }

  public void setHeightIndex(int index) {
    int numHeights = elevatorHeights.length - 1;

    if (elevatorHeightIndex > numHeights) {
      elevatorHeightIndex = numHeights;
    }

    if (!(index > numHeights) && !(index < 0)) {
      elevatorHeightIndex = index;
    }
  }

  public void incerementHeight() {
    // Increment and Decrement are effectivly flipped when inverted
    // This is how we show this
    int value = (isInverted) ? 1 : -1;
    setHeightIndex(elevatorHeightIndex + value);
  }

  public void decrementHeight() {
    // Increment and Decrement are effectivly flipped when inverted
    // This is how we show this
    int value = (isInverted) ? -1 : 1;
    setHeightIndex(elevatorHeightIndex + value);
  }

  private double unitsToAngle(double units) {
    return 0;
  }

  private double angleToUnits(double angle) {
    return 0;
  }

  private void zeroSensors() {
    motor1.getSensorCollection().setQuadraturePosition(0, 0);
    motor2.getSensorCollection().setQuadraturePosition(0, 0);
  }
}
