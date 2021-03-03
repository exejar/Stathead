package me.dooger.betterlvlhead.champstats.statapi.exception;

import me.dooger.betterlvlhead.utils.References;

public class ApiRequestException extends Exception {

    public ApiRequestException() {
        System.err.println(References.MODNAME + " Api Request UnSuccessful");
    }

}
