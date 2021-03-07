package me.exejar.stathead.champstats.statapi.exception;

import me.exejar.stathead.utils.References;

public class InvalidKeyException extends Exception {

    public InvalidKeyException() {
        System.err.println(References.MODNAME + " Invalid API Key");
    }

}
