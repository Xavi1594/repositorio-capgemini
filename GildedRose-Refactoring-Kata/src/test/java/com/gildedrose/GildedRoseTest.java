package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

   
    @ParameterizedTest
    @CsvSource({
            "'foo', 0, 0, 'foo'",
            "'Aged Brie', 2, 0, 'Aged Brie'",
            "'Sulfuras, Hand of Ragnaros', 0, 80, 'Sulfuras, Hand of Ragnaros'",
            "'Backstage passes to a TAFKAL80ETC concert', 15, 20, 'Backstage passes to a TAFKAL80ETC concert'"
    })
    public void testItemName(String name, int sellIn, int quality, String expectedName) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedName, app.items[0].name);
    }

    @ParameterizedTest
    @CsvSource({
            "'Aged Brie', 2, 0, 1",
            "'Sulfuras, Hand of Ragnaros', 0, 80, 80",
            "'Backstage passes to a TAFKAL80ETC concert', 15, 20, 21",
            "'Sulfuras, Hand of Ragnaros', 0, 80, 80"
    })
    public void testItemQuality(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
            "'foo', 0, 0, 0",
            "'Aged Brie', 2, 0, 1",
            "'Aged Brie', 2, 50, 50",
            "'Sulfuras, Hand of Ragnaros', 0, 80, 80"
    })
    public void testQualityIsNeverNegative(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertTrue(app.items[0].quality >= 0);
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
            "'Backstage passes to a TAFKAL80ETC concert', 10, 20, 22",
            "'Backstage passes to a TAFKAL80ETC concert', 5, 20, 23",
            "'Backstage passes to a TAFKAL80ETC concert', 0, 20, 0"
    })
    public void testBackstagePassesQuality(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
            "'foo', 0, 20, 18",
            "'foo', -1, 20, 18"
    })
    public void testNormalItemDecreasesTwiceAsFastAfterSellIn(String name, int sellIn, int quality,
            int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
            "'Aged Brie', 0, 20, 22",
            "'Aged Brie', -1, 20, 22"
    })
    public void testAgedBrieIncreasesTwiceAsFastAfterSellIn(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
            "'Sulfuras, Hand of Ragnaros', 0, 80, 80",
            "'Sulfuras, Hand of Ragnaros', 10, 80, 80"
    })
    public void testSulfurasQualityIsAlwaysEighty(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
            "'foo', 10, 0, 0",
            "'foo', 5, 0, 0"
    })
    public void testNormalItemQualityIsNotNegative(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertTrue(app.items[0].quality >= 0);
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
            "'Aged Brie', 10, 50, 50",
            "'Aged Brie', 5, 50, 50"
    })
    public void testAgedBrieQualityDoesNotExceedFifty(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedQuality, app.items[0].quality);
    }

    @ParameterizedTest
    @CsvSource({
        "'Conjured Mana Cake', 10, 20, 18",
        "'Conjured Mana Cake', 5, 10, 8",
        
    })
    public void testConjuredItemDegradesQualityTwiceAsFast(String name, int sellIn, int quality, int expectedQuality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(expectedQuality, app.items[0].quality);
    }
}
