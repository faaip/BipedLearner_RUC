package MDP;

import java.util.LinkedHashSet;
import java.util.Set;
import Rendering_dyn4j.ExampleGraphics2D;

// Almost directly copied from CellWorldAction

public enum Action {
    Left, Right, None;

    private static final Set<Action> _actions = new LinkedHashSet<>();

    static {
        _actions.add(Left);
        _actions.add(Right);
    }

    public static final Set<Action> get_actions() {
        return _actions;
    }

    public boolean isNoOp() {
        if (None == this) {
            return true;
        } else return false;
    }

    public void getResultOfAction() {
        switch (this) {
            case Left:
                ExampleGraphics2D.balancer.lean(-2);
                break;
            case Right:
                ExampleGraphics2D.balancer.lean(2);
                break;
        }
    }


}
