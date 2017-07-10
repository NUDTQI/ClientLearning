package agents.NUDTQI;

/**
 * Created by qi on 05.07.2017.
 * agent controller for single player learning track of GVGAI2017
 */

import serialization.SerializableStateObservation;
import serialization.Types;
import utils.ElapsedCpuTimer;

import java.util.Random;

import agents.NUDTQI.Sarsa;

/**
 * This class has been built with a simple design in mind.
 * It is to be used to store player agent information,
 * to be later used by the client to send and receive information
 * to and from the server.
 */
public class Agent extends utils.AbstractPlayer {
	
	private Sarsa qlearner; 
	
	private RLAction lastact;
	
	private RLAction curact;
	
	private RLState laststate;
	
	private RLState curstate;
	
	private float reward;
	

    /**
     * Public method to be called at the start of the communication. No game has been initialized yet.
     * Perform one-time setup here.
     */
    public Agent(SerializableStateObservation sso, ElapsedCpuTimer elapsedTimer){
    	
    	
    	qlearner = new Sarsa(); 
    	
    	this.init(sso,elapsedTimer);
    }


    
    /**
     * Public method to be called at the start of every level of a game.
     * Perform any level-entry initialization here.
     * @param sso Phase Observation of the current game.
     * @param elapsedTimer Timer (1s)
     */
    @Override
    public void init(SerializableStateObservation sso, ElapsedCpuTimer elapsedTimer){

    	//qlearner.setParameters(in_gamma, in_beta, in_exploration);
    	qlearner.initActionList(sso);
    }

    /**
     * Method used to determine the next move to be performed by the agent.
     * This method can be used to identify the current state of the game and all
     * relevant details, then to choose the desired course of action.
     *
     * @param sso Observation of the current state of the game to be used in deciding
     *            the next action to be taken by the agent.
     * @param elapsedTimer Timer (40ms)
     * @return The action to be performed by the agent.
     */
    @Override
    public Types.ACTIONS act(SerializableStateObservation sso, ElapsedCpuTimer elapsedTimer){
      
    	laststate = curstate;
        curstate = qlearner.GetStateFromEnv(sso);
        
        lastact = curact;
        curact = qlearner.chooseAction(curstate);
        
        reward = sso.gameScore;
        
        qlearner.update(laststate, lastact, reward, curstate, curact);
		
        Types.ACTIONS act = curact.GetAction();
        
        return act;
    }
 
    /**
     * Method used to perform actions in case of a game end.
     * This is the last thing called when a level is played (the game is already in a terminal state).
     * Use this for actions such as teardown or process data.
     *
     * @param sso The current state observation of the game.
     * @param elapsedTimer Timer (up to CompetitionParameters.TOTAL_LEARNING_TIME
     * or CompetitionParameters.EXTRA_LEARNING_TIME if current global time is beyond TOTAL_LEARNING_TIME)
     * @return The next level of the current game to be played.
     * The level is bound in the range of [0,2]. If the input is any different, then the level
     * chosen will be ignored, and the game will play a random one instead.
     */
    @Override
    public int result(SerializableStateObservation sso, ElapsedCpuTimer elapsedTimer){
        Random r = new Random();
        Integer level = r.nextInt(3);

        return level;
    }

}
