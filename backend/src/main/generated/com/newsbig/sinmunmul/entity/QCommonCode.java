package com.newsbig.sinmunmul.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommonCode is a Querydsl query type for CommonCode
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommonCode extends EntityPathBase<CommonCode> {

    private static final long serialVersionUID = 1232551837L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommonCode commonCode = new QCommonCode("commonCode");

    public final NumberPath<Integer> code = createNumber("code", Integer.class);

    public final NumberPath<Integer> codeOrder = createNumber("codeOrder", Integer.class);

    public final NumberPath<Integer> codeSeq = createNumber("codeSeq", Integer.class);

    public final QCommonCodeGroup commonCodeGroup;

    public final StringPath delYn = createString("delYn");

    public final StringPath modDt = createString("modDt");

    public final StringPath modId = createString("modId");

    public final StringPath regDt = createString("regDt");

    public final StringPath regId = createString("regId");

    public final StringPath value = createString("value");

    public QCommonCode(String variable) {
        this(CommonCode.class, forVariable(variable), INITS);
    }

    public QCommonCode(Path<? extends CommonCode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommonCode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommonCode(PathMetadata metadata, PathInits inits) {
        this(CommonCode.class, metadata, inits);
    }

    public QCommonCode(Class<? extends CommonCode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commonCodeGroup = inits.isInitialized("commonCodeGroup") ? new QCommonCodeGroup(forProperty("commonCodeGroup")) : null;
    }

}

