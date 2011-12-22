package frontline.model.util;
/*
 * Copyright (c) 2011 BleepBloop Software, All Rights Reserved
 *
 * Unpublished copyright.  All rights reserved.  
 */
/**
 * Basically handles two values so as not to have to be dealing with two floats.
 *
 * @version 1.00, submitted by Nicolas Chavez
 * @author chavenic: Jul 25, 2011, 8:57:36 AM
 */
public class Vector2 {
    
    public float X, Y;//The two dimensions.
    
    public Vector2(){
        X = 0f;
        Y = 0f;
    }
    
    //The only constructor, takes int both dimensions.
    public Vector2(float x, float y){
        X = x;
        Y = y;
    }
    
    public String toString(){
            return "X: " + X + ", Y: " + Y;
    }
}
