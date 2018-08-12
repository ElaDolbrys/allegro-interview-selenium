package pl.allegro.interview;

import org.junit.Assert;
import org.junit.Test;


public class Tests {

    @Test
    public void loadConfigurationTest() {
        String url = ApplicationConfiguration.getApplicationUrl();

        System.out.println("url: " + url);

        Assert.assertNotNull(url);
        Assert.assertTrue(url.startsWith("http"));
        Assert.assertTrue(url.endsWith("/"));

    }

    @Test
    public void openApplicationTest() {
        BaseApplication
                .forUrl(ApplicationConfiguration.getApplicationUrl())
                .open()
                .clickOn("//div[@data-role='rodo-modal-body']//button[@data-role='accept-consent']")
                .close();
    }

    @Test
    public void findAndAddItemToCart() {
        String itemTitleFromCart;
        String itemTitleFromList;
        BaseApplication baseApplication =
        BaseApplication

         // 1. otworzyć stronę allegro.pl
                .forUrl(ApplicationConfiguration.getApplicationUrl())
                .open()
                .clickOn("//div[@data-role='rodo-modal-body']//button[@data-role='accept-consent']")

        // 2. wyszukać dowolny, używany przedmiot droższy niż 200zł
                .insertText("//input[@class='metrum-search__query ']","pracy")
                .clickOn("//form[@class='metrum-search']//input[@type='submit']")
                .insertText("//input[@id='price_from']","200")
                .pressEnter("//input[@id='price_from']");

        // 3. wejść na stronę przedmiotu
                itemTitleFromList = baseApplication.getTextFromElement("//div[@data-box-name='Items Container']//article[@data-analytics-view-custom-index0='1']//a[@title]");
                    baseApplication
                .clickOn("//div[@data-box-name='Items Container']//article[@data-analytics-view-custom-index0='1']//a[@title]")

        // 4. dodać go do koszyka
                .clickOn("//a[@id='add-to-cart']")

        // 5. sprawdzić, czy dodany przedmiot jest w koszyku
                .ensureXPathElementExists("//m-price");
                itemTitleFromCart = baseApplication.getTextFromElement("//div[@class='offer-title--cell']//a");
                Assert.assertTrue(itemTitleFromCart.contains(itemTitleFromList));
                    baseApplication
                .close();
    }

}
