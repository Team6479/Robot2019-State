/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.commands.IntakeStateTracker;
import frc.robot.states.StateMachine;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakePivoter;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public enum IntakeState {
    BALL, HATCH
  }

  public enum PivotState {
    NORMAL, INVERTED
  }

  public static StateMachine<IntakeState> intakeState;
  public static StateMachine<PivotState> pivotState;
  public static OI oi;
  public static Intake intake;
  public static IntakePivoter intakePivoter;

  public Robot() {
  }

  @Override
  public void robotInit() {
    intakeState = new StateMachine<>();
    pivotState = new StateMachine<>();

    new IntakeStateTracker().start();

    intake = new Intake();
    intakePivoter = new IntakePivoter();

    oi = new OI();
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
