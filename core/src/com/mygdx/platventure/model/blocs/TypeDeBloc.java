package com.mygdx.platventure.model.blocs;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public enum TypeDeBloc {

    PLAYER("Player", true, false, 0.5f, 0.5f, 0.1f),
    SOL("Sol", false, false, 1f, 0.25f, 0.1f),
    SORTIE("Sortie", false, true, 0f, 0f, 0f),
    GEMME("Gemme", false, true, 0f, 0f, 0f),
    EAU("Eau", false, true, 0f, 0f, 0f);

    private final String name;
    private final boolean hasGravity;
    private final boolean isSensor;
    private final float density;
    private final float friction;
    private final float restitution;

    TypeDeBloc(String name, boolean hasGravity, boolean isSensor, float density, float friction, float restitution)
    {
        this.name = name;
        this.hasGravity = hasGravity;
        this.isSensor = isSensor;
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }

    public BodyDef getBodyDef()
    {
        BodyDef body = new BodyDef();
        body.type = (hasGravity) ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        body.bullet = false;
        body.active = true;
        body.awake = hasGravity;
        body.allowSleep = false;
        body.fixedRotation = true;
        return body;
    }

    public FixtureDef getFixtureDef()
    {
        FixtureDef fixture = new FixtureDef();
        fixture.isSensor = this.isSensor;
        fixture.restitution = this.restitution;
        fixture.friction = this.friction;
        fixture.density = this.density;
        return fixture;
    }

    public boolean hasGravity() {
        return hasGravity;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public String getName() {
        return name;
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

    public static TypeDeBloc get(String name)
    {
        for(TypeDeBloc type : TypeDeBloc.values())
        {
            if(type.name.equals(name))
                return type;
        }
        return null;
    }

}
