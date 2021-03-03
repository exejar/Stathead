package me.dooger.betterlvlhead.champstats.statapi.exception;

import me.dooger.betterlvlhead.utils.References;

public class PlayerNullException extends Exception {

    public PlayerNullException() {
        System.err.println(References.MODNAME + " Player returned as null");;
    }

}