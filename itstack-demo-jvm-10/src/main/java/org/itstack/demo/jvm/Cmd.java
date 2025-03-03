package org.itstack.demo.jvm;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.List;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 */
public class Cmd {

    @Parameter(names = {"-?", "-help"}, description = "print help message", order = 3, help = true)
    public boolean helpFlag = false;

    @Parameter(names = "-version", description = "print version and exit", order = 2)
    boolean versionFlag = false;

    @Parameter(names = "-verbose", description = "enable verbose output", order = 5)
    public boolean verboseClassFlag = false;

    @Parameter(names = {"-cp", "-classpath"}, description = "classpath", order = 1)
    public String classpath;

    @Parameter(names = "-Xjre", description = "path to jre", order = 4)
    public String jre;

    @Parameter(names = "-args", description = "ext in args", order = 6)
    public String args;

    @Parameter(description = "main class and args")
    public List<String> mainClassAndArgs;

    public boolean ok;

    public String getMainClass() {
        return mainClassAndArgs != null && !mainClassAndArgs.isEmpty()
                ? mainClassAndArgs.get(0)
                : null;
    }

    public List<String> getAppArgs() {
        return mainClassAndArgs != null && mainClassAndArgs.size() > 1
                ? mainClassAndArgs.subList(1, mainClassAndArgs.size())
                : null;
    }

    public static Cmd parse(String[] argv) {
        Cmd args = new Cmd();
        JCommander cmd = JCommander.newBuilder().addObject(args).build();
        cmd.parse(argv);
        args.ok = true;
        return args;
    }

}


