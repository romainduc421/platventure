package com.mygdx.platventure.model.builder;

public enum BodyType {
    BRICK_A("Brick_A"), BRICK_B("Brick_B"), BRICK_C("Brick_C"), BRICK_D("Brick_D"), BRICK_E("Brick_E"), BRICK_F("Brick_F"), BRICK_G("Brick_G"), BRICK_H("Brick_H"), BRICK_I("Brick_I"),
    WATER("Water"),
    CENTRAL_PLATFORM("Platform_J"), LEFT_PLATFORM("Platform_K"), RIGHT_PLATFORM("Platform_L"),
    PLAYER("Player");

    private final String type;

    BodyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
