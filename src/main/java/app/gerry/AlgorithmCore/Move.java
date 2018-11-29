package app.gerry.AlgorithmCore;

import app.gerry.Geography.Chunk;
import app.gerry.Geography.District;

public class Move {
    public Chunk moveable;
    public District sourceDistrict;
    public District destinationDistrict;
    public double newSrcVal;
    public double newDestVal;

    public void Move(Chunk moveable , District src, District dest){
        return;
    }
    public double getGain(){
        return 0;
    }
    public void setValues(double src, double dest){
        return;
    }
}
