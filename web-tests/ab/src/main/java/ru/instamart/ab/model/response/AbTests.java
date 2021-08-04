package ru.instamart.ab.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.instamart.ab.service.Mapper;

import java.util.Date;
import java.util.List;

@Data
public final class AbTests {

    public List<Test> tests;
    private int abTestsCount;

    @Data
    private static final class Test {
        public String id;
        public String name;
        public String linkToTask;
        public int limit;
        public int offset;
        public List<Group> groups;
        public String status;
        public Date createdAt;
        public String createdBy;
        public Date updatedAt;
        public Date startDate;
        public Date endDate;
        public String eventFilter;
        public String salt;
        public String label;
        public List<Object> platforms;
        public List<Object> opZones;
    }

    @Data
    private static final class Group {
        public String id;
        public String name;
        public String description;
        public int limit;
        public Params params;
        public boolean isReference;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static final class Params {
        private int feature_state;
        private String name;
    }
}
