package eu.swiec.bearballin.common.tests;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.util.List;
import java.util.Locale;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.FilePrintStreamFactory.ResolveToSimpleName;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.ParameterConverters;


public class JBehaveTests extends JUnitStories {

    @Override
    public Configuration configuration() {
        Keywords keywords = new LocalizedKeywords(new Locale("pl"));

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
        return new InstanceStepsFactory(configuration(), new FileIOScenario(), new PathStory());
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), "**/historyjki/*.historyjka", "**/failing_before*.historyjka");
    }
}

