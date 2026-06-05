package com.ensa.akinator.Utils;

import com.ensa.akinator.Models.Character;
import com.ensa.akinator.Models.Player;

public class Global {
    public static Character characterFounded;
    public static AudioPlayer audioPlayer = new AudioPlayer();
    public static Player loggedInPlayer = null;
    public static int lastMatchScore = 0;
}
