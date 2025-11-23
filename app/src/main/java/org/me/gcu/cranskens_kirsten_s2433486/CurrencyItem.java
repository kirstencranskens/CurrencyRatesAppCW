package org.me.gcu.cranskens_kirsten_s2433486;

public class CurrencyItem {
    private String title;
    private String link;
    private String pubDate;
    private String description;
    private float rate;

    public CurrencyItem() {
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getPubDate() { return pubDate; }
    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public float getRate() { return rate; }
    public void setRate(float rate) { this.rate = rate; }

    @Override
    public String toString() {
        // Just show title + rate – the date is on the detail screen anyway
        return title + "  |  " + rate;
    }

}
