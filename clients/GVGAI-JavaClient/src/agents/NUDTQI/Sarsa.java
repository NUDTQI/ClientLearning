/*
 * created by Qi 7/7/2017
 * Sarsa algorithm for Agent learner
 */
package agents.NUDTQI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Random;

import serialization.SerializableStateObservation;

/**
 *
 * @author Qi
 */
public class Sarsa {
	
    HashMap<RLState, HashMap<RLAction, Double>> qf;
    
    public ArrayList<RLAction> ActionList;
    public ArrayList<RLState> StateList;

    public RLAction defaultAction;

    private double gamma;
    private double beta;
    private double exploration; 
    private double descend;

    private Random r = new Random();
    

    public Sarsa() {
    	
    	qf = new HashMap<RLState, HashMap<RLAction, Double>>();
   
        gamma = 0.95;
        beta = 1.0;
        exploration = 0.8; 
        descend = 0.99999;
    }
    
//    parameter set
    public void setParameters(double in_gamma, double in_beta, double in_exploration) {	
        gamma = in_gamma;
        beta = in_beta;
        exploration = in_exploration;
    }

//    get an random action from action list
    private RLAction getRandomAct(){
    	
    	int index = r.nextInt(ActionList.size());

    	return ActionList.get(index);
    }

//    initialize action list according to received game state at beginning
    public void initActionList(SerializableStateObservation sso){
    	
    	for (int i=0;i<sso.availableActions.size();i++){

    		RLAction act = new RLAction(sso.availableActions.get(i));
   
    		ActionList.add(act);
    	}
    }
    
    public RLAction chooseAction(RLState state) {
    	
        if (r.nextDouble() < exploration){	
            return this.getRandomAct();
        }
        
        if (qf.containsKey(state)) {
        	
//            Greedy action choosing
            RLAction act = defaultAction;
            double max = Double.NEGATIVE_INFINITY;
            
            HashMap<RLAction, Double> actions = qf.get(state);
            for (RLAction a : actions.keySet()) {
                if (actions.get(a) > max) {
                    act = a;
                    max = actions.get(a);
                }
            }
            
            return act;
        }

        return defaultAction;
    }

//    learning function, update q value
    public void update(RLState state, RLAction action, double reward, RLState nextState, RLAction nextAction) {
        
    	double error = reward + gamma*getQ(nextState, nextAction) - getQ(state, action);
        if (error != 0) {
            System.out.println(""+state+" + "+action+" -> "+nextState+" err="+error);
        }
        
        setQ(state, action, getQ(state, action)+beta*error);
        
        // learning rate and exploration rate adjust
        beta = beta*descend;
        exploration = exploration*descend;
        if (exploration < 0.01){
            exploration = 0.01;
        }
        if (beta < 0.05){
            beta = 0.05;
        }
    }
    
//    set q value for state-action pair
    private void setQ(RLState state, RLAction action, double val) {
        
        if ( ! qf.containsKey(state)) {
            qf.put(state, new HashMap<RLAction, Double>());
        }

        HashMap<RLAction, Double> mapAV = qf.get(state);

        if ( ! mapAV.containsKey(action)) {
        	mapAV.put(action, val);
            return;
        }
        
        mapAV.remove(action);
        mapAV.put(action, val);
    }

//    get q value of state-action pair
    private double getQ(RLState state, RLAction action) {

        if ( ! qf.containsKey(state)) {
            qf.put(state, new HashMap<RLAction, Double>());
        }

        HashMap<RLAction, Double> map = qf.get(state);

        if ( ! map.containsKey(action)) {
            map.put(action, 0d);
            return 0;
        }

        return map.get(action); 
    }

//    get max q value in table
    private double getMaxQ(RLState state) {
        
    	double max = Double.NEGATIVE_INFINITY;
    	
        if (qf.containsKey(state)) {
//            Greedy action choosing           
            HashMap<RLAction, Double> actions = qf.get(state);
            for (RLAction a : actions.keySet()) {
                if (actions.get(a) > max){
                    max = actions.get(a);
                }
            }
        }
        else {
            //if the state is new...
            qf.put(state, new HashMap<RLAction, Double>());
            return 0;
        }
        
        //if there were no actions, we return 0
        if (max == Double.NEGATIVE_INFINITY){
        	return 0;
        }
        else {
        	return max;
        }
    }

    @Override
    public String toString() {
    	
        String ret = "QFunction states: "+qf.keySet().size()+"\n";
        
        for (RLState s : qf.keySet()) {
            ret+="\nstate: "+s+" actions: "+qf.get(s).keySet().size()+"\n";
            RLAction besta = null;
            double maxq = Double.NEGATIVE_INFINITY;
            
            for (RLAction a : qf.get(s).keySet()) {
                if (qf.get(s).get(a).isNaN() || qf.get(s).get(a).isInfinite())
                    ret+="\n NaN or Inf !!\n";
                if (qf.get(s).get(a) > maxq) {
                    maxq = qf.get(s).get(a);
                    besta = a;
                }
            }
            
            if (besta != null)
                ret+=besta.toString()+" -> "+qf.get(s).get(besta)+"\n";
            else{
            	ret+= "NOACTIONS";
            }
        }
        
        return ret;
    }
    
//    get best evaluation for each state
    public HashMap<RLState, Double> getStatesWithValues() {
    	
        HashMap<RLState, Double> ret = new HashMap<RLState, Double>();
        
        for (RLState s : qf.keySet()) {
            Double v = getMaxQ(s);
            ret.put(s, v);
        }
        
        return ret;
    }
    
//    abstract a RLState from original game state
    public RLState GetStateFromEnv(SerializableStateObservation sso){
    	
//    	RLState newst;
//    	newst.DiscreteDis(sso);
//    	
//    	RLState stIntable = findStateInTable(newst);
//    	
//    	if(stIntable == null){
//    		stIntable = new RLState(newst);
//    		StateList.add(stIntable);
//    	}
    	
//    	return stIntable;
    	return null;
    }
    
//    
    private RLState findStateInTable(RLState rs){
    	
    	for (int i=0;i<StateList.size();i++) {
    		
    		RLState temp = StateList.get(i);
    		if(rs.equals(temp)){
    			return temp;
    		}
        }
    	
    	return null;
    }
}
