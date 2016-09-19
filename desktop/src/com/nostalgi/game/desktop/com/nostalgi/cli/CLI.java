package com.nostalgi.game.desktop.com.nostalgi.cli;

import java.util.HashMap;

/**
 * Created by ksdkrol on 2016-09-19.
 */
public class CLI {

    private HashMap<String, String> args = new HashMap();

    public CLI(String[] args) {
        for(int i = 0; i < args.length; i++) {
            if(args[i].contains("-") || args[i].contains("--")) {
                this.args.put(args[i].replace("-","").replace("--", ""), args[i+1]);
                i++;
            } else {
                this.args.put("arg"+i, args[i]);
            }
        }
    }

    public HashMap<String, String> getRawArgs() {
        return args;
    }

    public boolean getBooleanArg(String arg) {
        if(this.args.containsKey(arg)) {
            if (this.args.get(arg).equals("1")) {
                return true;
            }
            return Boolean.parseBoolean(this.args.get(arg));
        } else {
            return false;
        }
    }

    public float getFloatArg(String arg) {
        try {
            return Float.parseFloat(this.args.get(arg));
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public float getFloatArg(String arg, float def) {
        if(this.args.containsKey(arg)) {
            return getFloatArg(arg);
        }
        return def;
    }

    public int getIntArg(String arg) {
        return Integer.parseInt(this.args.get(arg));
    }

    public int getIntArg(String arg, int def) {
        if(this.args.containsKey(arg)) {
            return getIntArg(arg);
        }
        return def;
    }

    public String getStringArg(String arg) {
        if(this.args.containsKey(arg)) {
            return this.args.get(arg);
        }
        return "";
    }

    public String getStringArg(String arg, String def) {
        if(this.args.containsKey(arg)) {
            return this.args.get(arg);
        }
        return def;
    }
}
