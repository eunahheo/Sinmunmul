package com.newsbig.sinmunmul.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNews is a Querydsl query type for News
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNews extends EntityPathBase<News> {

    private static final long serialVersionUID = 1141730232L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNews news = new QNews("news");

    public final QCommonCode commonCode;

    public final QCommonCodeGroup commonCodeGroup;

    public final StringPath delYn = createString("delYn");

    public final StringPath modDt = createString("modDt");

    public final StringPath modId = createString("modId");

    public final StringPath newsAuthor = createString("newsAuthor");

    public final StringPath newsAuthorEmail = createString("newsAuthorEmail");

    public final StringPath newsDesc = createString("newsDesc");

    public final StringPath newsLink = createString("newsLink");

    public final StringPath newsModDt = createString("newsModDt");

    public final StringPath newsPhoto = createString("newsPhoto");

    public final StringPath newsPress = createString("newsPress");

    public final StringPath newsRegDt = createString("newsRegDt");

    public final NumberPath<Long> newsSeq = createNumber("newsSeq", Long.class);

    public final StringPath newsSummary = createString("newsSummary");

    public final StringPath newsTitle = createString("newsTitle");

    public final StringPath regDt = createString("regDt");

    public final StringPath regId = createString("regId");

    public QNews(String variable) {
        this(News.class, forVariable(variable), INITS);
    }

    public QNews(Path<? extends News> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNews(PathMetadata metadata, PathInits inits) {
        this(News.class, metadata, inits);
    }

    public QNews(Class<? extends News> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commonCode = inits.isInitialized("commonCode") ? new QCommonCode(forProperty("commonCode"), inits.get("commonCode")) : null;
        this.commonCodeGroup = inits.isInitialized("commonCodeGroup") ? new QCommonCodeGroup(forProperty("commonCodeGroup")) : null;
    }

}

