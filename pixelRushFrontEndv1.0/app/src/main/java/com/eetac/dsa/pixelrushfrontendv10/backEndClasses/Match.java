package com.eetac.dsa.pixelrushfrontendv10.backEndClasses;

import java.util.ArrayList;
import java.util.List;
public class Match {
    String username;
    int totalPoints;
    int currentLVL;
    int maxLVL;
    List<Integer> pointsObtainedPerLevel;

    //fully constructor
    public Match(String username) {
        this.username = username;
        this.totalPoints = 0;//user always starts with 0 points
        this.currentLVL = 1;//user always start at level 1
        //This value will change at the end of the project because it depends on the number od levels we make, we start with 3
        this.maxLVL = 3;
        this.pointsObtainedPerLevel = new ArrayList<>();
    }
}
