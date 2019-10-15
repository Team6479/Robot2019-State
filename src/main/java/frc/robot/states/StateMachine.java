package frc.robot.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * StateMachine
 */
public class StateMachine<E extends Enum<E>> {
  private HashMap<E, HashMap<Subsystem, ArrayList<Runnable>>> stateHooks;
  private HashMap<E, Command> stateCommands;
  private E currentState;

  public StateMachine() {
    stateHooks = new HashMap<>();
    stateCommands = new HashMap<>();
    currentState = null;
  }

  public void addStateHooks(E state, Subsystem requires, Runnable... hooks) {
    if (!stateHooks.containsKey(state)) {
      HashMap<Subsystem, ArrayList<Runnable>> hooksMap = new HashMap<>();
      hooksMap.put(requires, new ArrayList<Runnable>(Arrays.asList(hooks)));
      stateHooks.put(state, hooksMap);
    } else if (!stateHooks.get(state).containsKey(requires)) {
      stateHooks.get(state).put(requires, new ArrayList<Runnable>(Arrays.asList(hooks)));
    } else {
      stateHooks.get(state).get(requires).addAll(Arrays.asList(hooks));
    }

    // Ensure command gets regenerated
    stateCommands.put(state, null);
  }

  public void addStateHooks(E state, Runnable... hooks) {
    addStateHooks(state, null, hooks);
  }

  public void setState(E state) {
    if (state != currentState) {
      currentState = state;
      if (stateCommands.get(state) == null) {
        generateCommand(state);
      }
      Scheduler.getInstance().add(stateCommands.get(state));
    }
  }

  public void generateCommand(E state) {
    CommandGroup commandGroup = new CommandGroup();
    var hooks = stateHooks.get(state);

    for (Subsystem requires : hooks.keySet()) {
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          for (Runnable hook : hooks.get(requires)) {
            hook.run();
          }
        }
      };

      Command command = null;
      if (requires != null) {
        command = new InstantCommand(requires, runnable);
      } else {
        command = new InstantCommand(runnable);
      }

      commandGroup.addParallel(command);
    }

    stateCommands.put(state, commandGroup);
  }
}
