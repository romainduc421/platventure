package com.mygdx.platventure.model.builder;

import com.badlogic.gdx.physics.box2d.*;

public class BodyFactory {

    private final BodyAspect aspect;
    private final BodyType type;

    public BodyFactory(BodyAspect bodyAspect, BodyType imageFactory) {
        this.aspect = bodyAspect;
        this.type = imageFactory;
    }

    public Body buildBody(World world, float x, float y, float width, float height) {

        BodyDef bodyDef = createBodyDef(x + 0.5f*width, y + 0.5f*height, aspect.getBodyType());
        Body body = world.createBody(bodyDef);

        for(ShapeFactory sf : aspect) {
            Shape shape = sf.build(width, height);

            FixtureDef fixtureDef = createFixtureDef(shape, aspect.getDensity(), aspect.getFriction(), aspect.getRestitution(), aspect.isSensor());

            body.createFixture(fixtureDef);

            shape.dispose();
        }

        body.setUserData(type);

        return body;
    }

    public BodyType getType() {
        return type;
    }

    private BodyDef createBodyDef(float x, float y, BodyDef.BodyType type) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = x;
        bodyDef.position.y = y;
        bodyDef.type = type;
        bodyDef.fixedRotation = true;

        return bodyDef;
    }

    private FixtureDef createFixtureDef(Shape shape, float density, float friction, float restitution, boolean isSensor) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.isSensor = isSensor;

        return fixtureDef;
    }

}
