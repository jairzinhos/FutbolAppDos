package org.beginningandroid.futbolappdos;

public class MatchModel {
    private String match, hour, cup, channel;

    public MatchModel(String match, String hour, String cup, String channel) {
        this.match = match;
        this.hour = hour;
        this.cup = cup;
        this.channel = channel;
    }

    public String getMatch() {
        return match;
    }

    public String getHour() {
        return hour;
    }

    public String getCup() {
        return cup;
    }

    public String getChannel() {
        return channel;
    }
}
