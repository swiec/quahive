package eu.swiec.bearballin.common.tests;

import junit.framework.Assert;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import eu.swiec.bearballin.common.io.Path;


public class PathStory {
    Path path;
    String dirNameTaken;
    String baseFileName;
    String fileNameTaken;
    String fileExtensionTaken;

    String expectedDirString = "C:\\Users\\z026184\\workspace\\themis\\themis-master\\src\\main\\java\\historyjki\\";

    @Given("sciezka do pliku")
    public void givenSciezkaDoPliku() {
        givenSciezkaDoPliku("C:\\Users\\z026184\\workspace\\themis\\themis-master\\src\\main\\java\\historyjki\\procesPOS1.historyjka");
    }

    @When("pobrana zostanie nazwa katalogu")
    @Alias("pobrana zostanie sciezka katalogu")
    public void whenPobranaZostanieNazwaKatalogu() {
        dirNameTaken = path.getBaseDir();
    }

    @When("pobrana zostanie nazwa pliku")
    public void whenPobranaZostanieNazwaPliku() {
        fileNameTaken = path.getFileName();
    }

    @Then("zwrocona zostanie sciezka pliku az do ostatniego slasha wlacznie")
    public void thenZwroconaZostanieSciezkaPliku() {
        Assert.assertEquals(dirNameTaken, expectedDirString);
    }


    @Given("<sciezka> do pliku:")
    public void givenSciezkaDoPliku(@Named("sciezka") String sciezka) {
        path = new Path(sciezka);
    }

    @Then("zwrocona zostanie <sciezkaKatalogu> az do ostatniego slasha wlacznie")
    public void thenZwroconaZostaniesciezkaKataloguAzDoOstatniegoSlashaWlacznie(@Named("sciezkaKatalogu") String sciezkaKatalogu) {
        Assert.assertEquals(sciezkaKatalogu, dirNameTaken);
    }

    @Then("zwrocona zostanie <nazwaPliku>")
    public void thenZwroconaZostanienazwaPliku(@Named("nazwaPliku") String nazwaPliku) {
        Assert.assertEquals(nazwaPliku, fileNameTaken);
    }

    @When("pobrane zostanie rozszerzenie pliku")
    public void whenPobraneZostanieRozszerzeniePliku() {
        fileExtensionTaken = path.getFileExtension();
    }

    @Then("zwrocone zostanie <rozszerzeniePliku>")
    public void thenZwroconeZostanierozszerzeniePliku(@Named("rozszerzeniePliku") String rozszerzeniePliku) {
        Assert.assertEquals(rozszerzeniePliku, fileExtensionTaken);
    }

    @When("pobrane zostanie podstawowa nazwa pliku")
    public void whenPobraneZostaniePodstawowaNazwaPliku() {
        baseFileName = path.getBaseFileName();
    }

    @Then("zwrocone zostanie <podstawowaNazwaPliku>")
    public void thenZwroconeZostaniepodstawowaNazwaPliku(@Named("podstawowaNazwaPliku") String podstawowaNazwaPliku) {
        Assert.assertEquals(podstawowaNazwaPliku, baseFileName);
    }


}
