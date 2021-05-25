package com.buaa.ops.Service.Emails;

public class ReminderEmail {

    private final String subject;

    private final String title;

    private final String greetings;

    private final String body;

    public ReminderEmail(String subject, String title, String greetings, String body) {
        this.subject = subject;
        this.title = title;
        this.greetings = greetings;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getGreetings() {
        return greetings;
    }

    public String getBody() {
        return body;
    }
}
