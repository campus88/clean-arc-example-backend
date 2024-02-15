package com.campus.clean.arc.mongo.article.queries;

import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.rcore.database.mongo.commons.query.AbstractExampleQuery;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class ArticleFiltersQuery extends AbstractExampleQuery {

    private final ArticleFilters filters;

    public ArticleFiltersQuery(ArticleFilters articleFilters) {
        super(articleFilters);
        this.filters = articleFilters;
    }

    @Override
    public Criteria getCriteria() {
        Criteria finalCondition = new Criteria();
        List<Criteria> currentConditions = new ArrayList<>();

        if(filters.getQuery() != null && filters.getQuery().isBlank() == false){
            String[] query = filters.getQuery().trim().split(" ");
            List<Criteria> textSearch = new ArrayList<>();

            for(String q: query){
                textSearch.add(Criteria.where("title").regex(q, "i"));
            }

            currentConditions.add(new Criteria().orOperator(textSearch.toArray(new Criteria[textSearch.size()])));
        }

        if(currentConditions.size() == 0) return finalCondition;
        if(currentConditions.size() == 1) return currentConditions.get(0);

        finalCondition.andOperator(currentConditions.toArray(new Criteria[currentConditions.size()]));
        return finalCondition;
    }
}
