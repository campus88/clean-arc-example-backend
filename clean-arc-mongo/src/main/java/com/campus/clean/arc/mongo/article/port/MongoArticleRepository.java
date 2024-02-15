package com.campus.clean.arc.mongo.article.port;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.campus.clean.arc.mongo.article.documents.ArticleDoc;
import com.campus.clean.arc.mongo.article.mapper.ArticleMapper;
import com.campus.clean.arc.mongo.article.queries.ArticleFiltersQuery;
import com.rcore.database.mongo.commons.port.impl.AbstractMongoRepository;
import com.rcore.database.mongo.commons.query.AbstractExampleQuery;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoArticleRepository
        extends AbstractMongoRepository<String, ArticleEntity, ArticleDoc, ArticleFilters>
        implements ArticleRepository {

    public MongoArticleRepository(MongoTemplate mongoTemplate) {
        super(ArticleDoc.class, new ArticleMapper(), mongoTemplate);
    }

    @Override
    protected AbstractExampleQuery getSearchQuery(ArticleFilters filters) {
        return new ArticleFiltersQuery(filters);
    }

    @Override
    public Long count() {
        return mongoTemplate.execute(this.documentClass, collection -> collection.estimatedDocumentCount());
    }


}
