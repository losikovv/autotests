package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminTaxonV1 extends BaseObject {

    @JsonSchema(required = true)
    private String id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    private String permalink;

    @JsonSchema(required = true)
    private String keywords;

    @JsonSchema(required = true)
    private Boolean published;

    @JsonSchema(required = true)
    @JsonProperty("published_on_home_page")
    private Boolean publishedOnHomePage;

    @JsonSchema(required = true)
    @JsonProperty("active_for_new")
    private Boolean activeForNew;

    @JsonSchema(required = true)
    @JsonProperty("view_as_leaf")
    private Boolean viewAsLeaf;

    @JsonSchema(required = true)
    @JsonProperty("export_to_yandex_market")
    private Boolean exportToYandexMarket;

    @JsonSchema(required = true)
    @JsonProperty("hide_from_nav")
    private Boolean hideFromNav;

    @JsonSchema(required = true)
    private Boolean suggested;

    @Null
    @JsonProperty("instamart_id")
    @JsonSchema(required = true)
    private Long instamartId;

    @Null
    @JsonProperty("icon_url")
    private String iconUrl;

    @Null
    @JsonProperty("alt_icon_url")
    private String altIconUrl;

    @JsonSchema(required = true)
    private List<String> tenants;

    @JsonSchema(required = true)
    private List<String> retailers;

    @Null
    @JsonProperty("taxonomies_parent")
    @JsonSchema(required = true)
    private AdminTaxonomiesParentV1 taxonomiesParent;
}
