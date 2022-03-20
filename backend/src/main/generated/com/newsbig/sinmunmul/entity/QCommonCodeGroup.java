package com.newsbig.sinmunmul.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonCodeGroup is a Querydsl query type for CommonCodeGroup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommonCodeGroup extends EntityPathBase<CommonCodeGroup> {

    private static final long serialVersionUID = -1707147038L;

    public static final QCommonCodeGroup commonCodeGroup = new QCommonCodeGroup("commonCodeGroup");

    public final NumberPath<Integer> cgOrder = createNumber("cgOrder", Integer.class);

    public final StringPath cgValue = createString("cgValue");

    public final NumberPath<Integer> codeGroup = createNumber("codeGroup", Integer.class);

    public final NumberPath<Integer> codeGroupSeq = createNumber("codeGroupSeq", Integer.class);

    public final StringPath delYn = createString("delYn");

    public final StringPath modDt = createString("modDt");

    public final StringPath modId = createString("modId");

    public final StringPath regDt = createString("regDt");

    public final StringPath regId = createString("regId");

    public QCommonCodeGroup(String variable) {
        super(CommonCodeGroup.class, forVariable(variable));
    }

    public QCommonCodeGroup(Path<? extends CommonCodeGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonCodeGroup(PathMetadata metadata) {
        super(CommonCodeGroup.class, metadata);
    }

}

