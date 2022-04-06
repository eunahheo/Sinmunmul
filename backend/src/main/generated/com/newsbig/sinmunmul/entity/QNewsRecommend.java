package com.newsbig.sinmunmul.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewsRecommend is a Querydsl query type for NewsRecommend
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNewsRecommend extends EntityPathBase<NewsRecommend> {

    private static final long serialVersionUID = -200510492L;

    public static final QNewsRecommend newsRecommend = new QNewsRecommend("newsRecommend");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final StringPath delYn = createString("delYn");

    public final StringPath keyword1 = createString("keyword1");

    public final StringPath keyword2 = createString("keyword2");

    public final StringPath keyword3 = createString("keyword3");

    public final StringPath modDt = createString("modDt");

    public final StringPath modId = createString("modId");

    public final NumberPath<Long> newsSeq = createNumber("newsSeq", Long.class);

    public final NumberPath<Long> recomSeq = createNumber("recomSeq", Long.class);

    public final StringPath regDt = createString("regDt");

    public final StringPath regId = createString("regId");

    public QNewsRecommend(String variable) {
        super(NewsRecommend.class, forVariable(variable));
    }

    public QNewsRecommend(Path<? extends NewsRecommend> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewsRecommend(PathMetadata metadata) {
        super(NewsRecommend.class, metadata);
    }

}

