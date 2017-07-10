package agents.NUDTQI;

import serialization.Types;

/**
 *
 * @author Qi
 */
public class RLAction implements Cloneable {

	private Types.ACTIONS action;
    
    public RLAction(Types.ACTIONS act){
    	
    	action = act;
    }
    
    public Types.ACTIONS GetAction(){
    	
    	return action;
    }

}
