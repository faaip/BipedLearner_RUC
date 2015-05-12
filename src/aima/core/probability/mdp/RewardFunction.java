package aima.core.probability.mdp;

/**
 * An interface for QLearning reward functions.
 * 
 * @param <S>
 *            the state type.
 * @author Ciaran O'Reilly
 * @author Ravi Mohan
 */
public interface RewardFunction<S> {
	
	/**
	 * Get the reward associated with being in state s.
	 * 
	 * @param s
	 *            the state whose award is sought.
	 * @return the reward associated with being in state s.
	 */
	double reward(S s);
}