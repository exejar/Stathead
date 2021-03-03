package me.dooger.betterlvlhead.champstats.statapi.exception;

import me.dooger.betterlvlhead.utils.References;

public class InvalidKeyException extends Exception {

    public InvalidKeyException() {
        System.err.println(References.MODNAME + " Invalid API Key");
    }

}
