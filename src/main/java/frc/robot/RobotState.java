/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class RobotState {
  public enum State {
    Ball(0, 0, 0), Hatch(0, 0, 0);

    private State(double... heights) {
      this.elevatorHeights = heights;
    }

    public double[] elevatorHeights;
  }

  private State robotState = State.Ball;

  public State getRobotState() {
    return robotState;
  }

  public void setRobotState(State state) {
    robotState = state;
  }
}
