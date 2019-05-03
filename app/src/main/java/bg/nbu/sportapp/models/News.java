package bg.nbu.sportapp.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class News {

    private String team;
    private Date date;
    private String content;

    public News(String team, Date date, String content) {
        this.team = team;
        this.date = date;
        this.content = content;
    }

    public News() {
        this.team = "";
        this.date = new Date();
        this.content = "";
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return new SimpleDateFormat("HH:mm YYYY-MM-dd", Locale.getDefault()).format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
