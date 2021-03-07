package me.exejar.stathead.champstats.statapi.exception;

import me.exejar.stathead.utils.References;

public class ApiRequestException extends Exception {

    public ApiRequestException() {
        System.err.println(References.MODNAME + " Api Request UnSuccessful");
    }

}
