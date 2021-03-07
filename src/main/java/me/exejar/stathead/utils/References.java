package me.exejar.stathead.utils;


import java.util.regex.Pattern;

public interface References {
    String PREFIX = ChatColor.RED + "----------------------[" + ChatColor.DARK_RED + "BLVLH" + ChatColor.RED + "]----------------------\n";
    String SUFFIX = "\n" + ChatColor.RED + "---------------------------------------------------";
    Pattern UUID = Pattern.compile("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}");
    Pattern UUID_NO_DASHES = Pattern.compile("\\w{32}");

    String MODNAME = "Better Levelhead";
    String MODID = "betterlevelhead";
    String VERSION = "1.0-DEV";

    String MODNAMEPREF = "[BLVLH]";
}
