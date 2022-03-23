package com.newsbig.sinmunmul.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInterest is a Querydsl query type for Interest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInterest extends EntityPathBase<Interest> {

    private static final long serialVersionUID = 847547791L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInterest interest = new QInterest("interest");

    public final QCommonCode commonCode;

    public final QCommonCodeGroup commonCodeGroup;

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Integer> interestSeq = createNumber("interestSeq", Integer.class);

    public final StringPath modDt = createString("modDt");

    public final StringPath modId = createString("modId");

    public final StringPath regDt = createString("regDt");

    public final StringPath regId = createString("regId");

    public final QUser user;

    public QInterest(String variable) {
        this(Interest.class, forVariable(variable), INITS);
    }

    public QInterest(Path<? extends Interest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInterest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInterest(PathMetadata metadata, PathInits inits) {
        this(Interest.class, metadata, inits);
    }

    public QInterest(Class<? extends Interest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commonCode = inits.isInitialized("commonCode") ? new QCommonCode(forProperty("commonCode"), inits.get("commonCode")) : null;
        this.commonCodeGroup = inits.isInitialized("commonCodeGroup") ? new QCommonCodeGroup(forProperty("commonCodeGroup")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

