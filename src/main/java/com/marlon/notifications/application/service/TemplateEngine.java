package com.marlon.notifications.application.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

public class TemplateEngine {

    private final MustacheFactory mf = new DefaultMustacheFactory();

    public String render(String templateText, Map<String, Object> context) {
        StringWriter writer = new StringWriter();
        mf.compile(new StringReader(templateText), "template").execute(writer, context);
        return writer.toString();
    }
}