package eu.swiec.bearballin.tools;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import eu.swiec.bearballin.runtime.graphs.MedGraph;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToSimpleName;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.ParameterConverters;
import org.junit.Test;


public class StoryRun extends JUnitStories {

    @Override
    @Test
    public void run() throws Throwable {
        Embedder embedder = configuredEmbedder();
        try {
            embedder.runStoriesAsPaths(storyPaths());
        } finally {
            embedder.generateCrossReference();
        }
    }

    @Override
    public Configuration configuration() {
        Keywords keywords = new LocalizedKeywords(new Locale("en"));

        return new MostUsefulConfiguration()
                .useKeywords(keywords)
                .useStepCollector(new MarkUnmatchedStepsAsPending(keywords))
                .useStoryParser(new RegexStoryParser(keywords))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withPathResolver(new ResolveToSimpleName())
                                .withDefaultFormats()
                                .withFormats(CONSOLE, TXT, HTML, XML)
                                .withFailureTrace(true)
                                .withKeywords(keywords))
                .useParameterConverters(new ParameterConverters());
    }


    @Override
    public InjectableStepsFactory stepsFactory() {
        List<Object> stepsList = new ArrayList<Object>(10);
        stepsList.addAll(new MedGraph().getStepsInstances());
        return new InstanceStepsFactory(configuration(), stepsList);

        //put classes with step definitions here (with methods annotated @Given,@When,@Then... )
    }

    private String readProperty(String propertyName, String exceptionMessage) throws RuntimeException {
        String readedProperty;

        readedProperty = System.getProperty(propertyName);

        if (readedProperty == null || readedProperty.isEmpty()) {
            System.out.println("ERROR: " + exceptionMessage);
            throw new RuntimeException(exceptionMessage);
        }

        return readedProperty;
    }

    @Override
    protected List<String> storyPaths() {
        List<String> stories;
        String testName;
        String relativeStoryPath;

        relativeStoryPath = readProperty(Main.STORIESPATH, Main.STORIESPATH + " property has not been set");
        testName = readProperty("TESTNAME", "TESTNAME property has not been set");

        stories = new StoryFinder().findPaths(CodeLocations.codeLocationFromPath(relativeStoryPath).getFile(), asList(testName), null);

        System.out.println("CodelocationFromArgu:" + CodeLocations.codeLocationFromPath("target/themis-slave-1.4-resources/").getFile());
        System.out.println("CodelocationFromProp:" + CodeLocations.codeLocationFromPath(relativeStoryPath).getFile());
        System.out.println("Stories:" + stories);

        if (stories.isEmpty()) {
            String errMsg = "No stories found, for specified arguments\nTESTNAME:" + testName + "\nSTORYBASE:" + relativeStoryPath;

            System.out.println(errMsg);
            throw new RuntimeException(errMsg);
        }
        return stories;
    }

    public static List<String> asList(String string) {
        List<String> list = new ArrayList<String>(1);
        list.add(string);
        return list;
    }

}
