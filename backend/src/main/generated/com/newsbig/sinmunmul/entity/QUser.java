package com.newsbig.sinmunmul.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1141951664L;

    public static final QUser user = new QUser("user");

    public final StringPath delYn = createString("delYn");

    public final StringPath modDt = createString("modDt");

    public final StringPath modId = createString("modId");

    public final StringPath regDt = createString("regDt");

    public final StringPath regId = createString("regId");

    public final NumberPath<Integer> userAge = createNumber("userAge", Integer.class);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userGender = createString("userGender");

    public final StringPath userPwd = createString("userPwd");

    public final NumberPath<Integer> userSeq = createNumber("userSeq", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

