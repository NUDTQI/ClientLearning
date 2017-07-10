/*
 * created by Qi 7/7/2017
 * state abstracter
 * for sarsa learning
 */


package agents.NUDTQI;

import com.sun.org.apache.xpath.internal.operations.Bool;

import serialization.SerializableStateObservation;

/**
 *
 * @author adminlt
 */
public class RLState implements Cloneable {
    
    public static enum Dis{
        none,
        far,
        medium,
        near
    }
    static public double farThreald = 10000;
    static public double mediumThreald = 3000;
    static public double nearThreald = 800;
    
    public static Dis DiscreteDis(double distance){
    	
        if(distance >= farThreald){
            return Dis.none;
        }
        else if(distance >= mediumThreald){
            return Dis.far;
        }
        else if(distance >= nearThreald){
            return Dis.medium;
        }
        else{
            return Dis.near;
        }
    }
    
    
    public static enum Health{
         Nomal,
         Low,
         Critical
    }
    static public int healthNormalThreald = 100;
    static public int healthLowThreald = 40;
    static public int healthCriThreald = 20;
    
    public static Health HealthLevel(int health){
    	
        if(health > healthLowThreald){
            return Health.Nomal;
        }
        else if(health > healthCriThreald){
            return Health.Low;
        }
        else{
            return Health.Critical;
        }
    }
    
    
    public static enum Ammo{ 
        Nomal,
        Low,
        Critical
    }
    public static Ammo AmmoLevel(int ammo){
    	
        if(ammo > healthLowThreald){
            return Ammo.Nomal;
        }
        else if(ammo > healthCriThreald){
            return Ammo.Low;
        }
        else{
            return Ammo.Critical;
        }
    }
   
    
    public enum Movement{
        walk,
        running,
        stop
    }
    
    protected void updateState() {
        return;
    }
    
    private Dis m_disToEnemy;
    private Dis m_disToRs;
    private Health m_health;   
    
    private int[] m_pos;
    
    public RLState(){
    	
    }
    
    public RLState(RLState sta){
    	
    	m_disToEnemy = sta.m_disToEnemy;
    }
    
    public boolean equals(RLState sta){
    	
    	if(sta.m_health==this.m_health){
    		return true;
    	}
    	
    	return false;
    }
}
