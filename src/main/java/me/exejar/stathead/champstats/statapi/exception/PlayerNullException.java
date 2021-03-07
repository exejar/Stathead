package me.exejar.stathead.champstats.statapi.exception;

import me.exejar.stathead.utils.References;

public class PlayerNullException extends Exception {

    public PlayerNullException() {
        System.err.println(References.MODNAME + " Player returned as null");;
    }

}