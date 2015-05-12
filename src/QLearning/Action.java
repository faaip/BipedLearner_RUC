package QLearning;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by SaraMac on 12/05/15.
 */
public enum Action {
    hip1Inc, hip1Dec, hip1Stop, hip1Relax,
    hip2Inc, hip2Dec, hip2Stop, hip2Relax,
    knee1Inc, knee1Dec, knee1Stop, knee1Relax,
    knee2Inc, knee2Dec, knee2Stop, knee2Relax,
    foot1Inc, foot1Dec, foot1Stop, foot1Relax,
    foot2Inc, foot2Dec, foot2Stop, foot2Relax;


    private static final Set<Action> _actions = new LinkedHashSet<>();

    static {
        _actions.add(hip1Inc);
        _actions.add(hip1Dec);
        _actions.add(hip1Stop);
        _actions.add(hip1Relax);
        _actions.add(hip2Inc);
        _actions.add(hip2Dec);
        _actions.add(hip2Stop);
        _actions.add(hip2Relax);
    }
    }

