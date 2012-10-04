package eu.swiec.bearballin.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.swiec.bearballin.common.io.Environment;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.jbehave.core.io.StoryFinder;
import org.junit.runner.JUnitCore;

public class Main {

    public static final String STORIESPATH = "STORIESDIR";     // environment
    // varialble name
    // for relative path
    // to STORIES
    // location

    private static final List<String> STORIES = initStories();

    /**
     * @param args
     */
    public static void main(final String[] args) {
        System.setProperty(STORIESPATH, Environment.getRelativePathFromProperty());
        final Options options = initializeOptions();
        parseOptions(args, options, new PosixParser(), new HelpFormatter());
    }

    private static List<String> initStories() {
        System.setProperty(STORIESPATH, Environment.getRelativePathFromProperty());
        List<String> stories;
        String basePath = System.getenv(STORIESPATH) == null ? "" : System.getenv(STORIESPATH);

        if (basePath.isEmpty()) {
            basePath = Environment.getRelativePathFromProperty();
        }

        stories = new StoryFinder().findPaths(basePath, StoryRun.asList("**/*.story"), null);

        return stories;
    }

    @SuppressWarnings("static-access")
    private static Options initializeOptions() {

        final Option property = OptionBuilder.withArgName("property=value")
                .hasArgs(2)
                .withValueSeparator()
                .withDescription("use value for given property")
                .create("D");

        final Option properties = OptionBuilder.withLongOpt("properties")
                .withArgName("variableName")
                .hasOptionalArg()
                .withDescription("show environment propErties and variables")
                .create("e");

        final Option runSpecified = OptionBuilder.withLongOpt("runSpec")
                .withArgName("StoryPath")
                .hasArg()
                .withDescription("runs specified story")
                .create("s");

        final Option testNumber = OptionBuilder.withLongOpt("invoke")
                .withType(int.class)
                .withArgName("test number")
                .hasArg()
                .withDescription("run test number (to see tests numeration run with --list arg.")
                .create("i");

        final Option targetMach = OptionBuilder
                .withLongOpt("targetAdd")
                .withType(String.class)
                .hasArg()
                .withArgName("target address")
                .withDescription("address where test will take place")
                .create("t");


        final Option runTests = new Option("r", "run", false, "runs all STORIES");
        final Option help = new Option("h", "help", false, "print this message");

        final Options options = new Options();
        options.addOption("l", "list", false, "lists available tests");
        options.addOption(help);
        options.addOption(property);
        options.addOption(runTests);
        options.addOption(runSpecified);
        options.addOption(properties);
        options.addOption(testNumber);
        options.addOption(targetMach);

        return options;
    }

    private static void parseOptions(final String[] args, final Options options, final CommandLineParser parser, final HelpFormatter hf) {
        try {
            final CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("D")) {
                if (cmd.getOptionValues("D").length == 2) {
                    System.setProperty(cmd.getOptionValues("D")[0], cmd.getOptionValues("D")[1]);
                }
            }
            if (cmd.hasOption("t")) {
                //set target variable
                System.setProperty("TARGETENV", cmd.getOptionValue("t"));
            }

            if (cmd.hasOption("l") || cmd.hasOption("list")) {
                // list available tests
                listAvailableTests();
            }

            // run all available tests
            if (cmd.hasOption("r") || cmd.hasOption("run")) {
                System.out.println("Not yet implemented");
            }

            if (cmd.hasOption("e")) {
                printoutEnvVariables();
                printoutProperties();
                if (cmd.getOptionValue("e") != null) {
                    System.out.println("Variable '" + cmd.getOptionValue("e") + "' has value: " + Environment.getPropOrVar(cmd.getOptionValue("e")));
                }
            }

            if (cmd.hasOption("i")) {
                try {
                    final int tCase = Integer.parseInt(cmd.getOptionValue("i"));
                    System.setProperty("TESTNAME", STORIES.get(tCase));
                    runTests();
                } catch (NumberFormatException e) {
                    hf.printHelp("syntax", options, true);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Test Index out of bound Exception!");
                    listAvailableTests();
                }
            }

            if (cmd.hasOption("s") || cmd.hasOption("runSpec")) {
                System.setProperty("TESTNAME", cmd.getOptionValue("s"));
                runTests();
            }
            if (cmd.hasOption("h") || !cmd.getArgList().isEmpty() || cmd.getOptions().length == 0) {
                hf.printHelp("syntax", options, true);
            }
        } catch (ParseException e) {
            hf.printHelp("syntax", options, true);
        }
    }

    private static void runTests() {
        JUnitCore.main("eu.swiec.themis.tools.StoryRun");
        generateDumbAntJUnitReport();
    }

    private static void listAvailableTests() {
        System.out.println("Available tests:");
        int testNum = 0;
        for (String testPath : STORIES) {
            System.out.println("[" + testNum + "] " + testPath);
            testNum++;
        }
    }


    private static void generateDumbAntJUnitReport() {
        final File dumbReport = new File("./DumbTEST.xml");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(dumbReport);
            fileWriter.append("<testsuite><testcase name=\"agregationTEsts\"></testcase></testsuite>");
            fileWriter.write("<testsuite><testcase name=\"agregationTEsts\"></testcase></testsuite>");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints out system environment variables (System thing - System.getenv()).
     */
    public static void printoutEnvVariables() {
        System.out.println("**********************************\n\nE N V   V A R I A B L E S --printout: \n\n**********************************");
        final Map<String, String> envs = System.getenv();
        for (String key : envs.keySet()) {
            System.out.println(key + ": \'" + envs.get(key) + "\'");
        }
    }

    /**
     * Prints out all properties (Java thing - System.getProperties()).
     */
    public static void printoutProperties() {
        final Properties sysP = System.getProperties();
        System.out.println("**********************************\n\nP R O P E R T I E S --printout:\n\n**********************************");
        sysP.list(System.out);
    }

}