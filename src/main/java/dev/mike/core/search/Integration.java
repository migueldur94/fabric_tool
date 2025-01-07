package dev.mike.core.search;

import java.util.List;

public class Integration {
    private String name;
    private String link;
    private String className;
    private List<String> preprocessors;
    private List<String> postprocessors;

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setPreprocessors(List<String> preprocessors) {
        this.preprocessors = preprocessors;
    }

    public void setPostprocessors(List<String> postprocessors) {
        this.postprocessors = postprocessors;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getClassName() {
        return className;
    }

    public List<String> getPreprocessors() {
        return preprocessors;
    }

    public List<String> getPostprocessors() {
        return postprocessors;
    }
}
