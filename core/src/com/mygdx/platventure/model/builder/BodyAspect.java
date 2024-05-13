package com.mygdx.platventure.model.builder;

import com.badlogic.gdx.physics.box2d.BodyDef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public enum BodyAspect implements Iterable<ShapeFactory>{

    BRICK(1f, 0.1f, 0.25f, false, BodyDef.BodyType.StaticBody, ShapeFactory.SQUARE),
    RECTANGLE(1f, 0.1f, 0.25f, false, BodyDef.BodyType.StaticBody,  ShapeFactory.RECTANGLE),
    LEFT_PLATFORM(1f, 0.1f, 0.25f, false, BodyDef.BodyType.StaticBody, ShapeFactory.BREAK_LEFT),
    RIGHT_PLATFORM(1f, 0.1f, 0.25f, false, BodyDef.BodyType.StaticBody,  ShapeFactory.BREAK_RIGHT),

    SENSOR_RECTANGLE(1f, 0.1f, 0.25f, true, BodyDef.BodyType.StaticBody,  ShapeFactory.RECTANGLE),

    PLAYER(0.5f, 0.1f, 0.5f, false, BodyDef.BodyType.DynamicBody,  ShapeFactory.DIAMOND, ShapeFactory.Circle);

    private final float density;
    private final float friction;
    private final float restitution;

    private final BodyDef.BodyType bodyType;
    private final List<ShapeFactory> shapeFactories = new ArrayList<>();

    private final Boolean sensor;

    BodyAspect(float density, float friction, float restitution, boolean isSensor, BodyDef.BodyType type, ShapeFactory ... shapeFactories) {
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
        this.shapeFactories.addAll(Arrays.asList(shapeFactories));
        this.bodyType = type;
        this.sensor = isSensor;
    }

    public float getDensity() {
        return density;
    }

    public float getFriction() {
        return friction;
    }

    public float getRestitution() {
        return restitution;
    }

    public BodyDef.BodyType getBodyType() {
        return bodyType;
    }

    public boolean isSensor() {
        return sensor;
    }

    @Override
    public Iterator<ShapeFactory> iterator() {
        return shapeFactories.iterator();
    }


}
