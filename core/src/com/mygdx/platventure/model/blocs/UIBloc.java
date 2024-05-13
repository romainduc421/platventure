package com.mygdx.platventure.model.blocs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public enum UIBloc {
    PLATEFORME_GAUCHE(TypeDeBloc.SOL, 1f, 0.75f, new float[]{-0.5f, 0.25f, 0.5f, 0.25f, 0.5f, -0.5f, 0, -0.5f, -0.5f, -0.125f}, false,new Texture("resources/images/Platform_J.png")),
    PLATEFORME_DROITE(TypeDeBloc.SOL, 1f, 0.75f, new float[]{-0.5f, 0.25f, 0.5f, 0.25f, 0.5f, -0.125f, 0, -0.5f, -0.5f, -0.5f}, false,new Texture("resources/images/Platform_L.png")),
    PLATEFORME_MILIEU(TypeDeBloc.SOL, 1f, 0.75f, new float[]{-0.5f, 0.25f, 0.5f, 0.25f, 0.5f, -0.5f, -0.5f, -0.5f}, false,new Texture("resources/images/Platform_K.png")),
    SOL(TypeDeBloc.SOL, 1f, 1f, new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f}),
    SORTIE(TypeDeBloc.SORTIE, 1f, 1f, new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f}, false,new Texture("resources/images/Exit_Z.png")),
    SORTIE_GAUCHE(SORTIE, new Texture("resources/images/Exit_Z.png")),
    SORTIE_DROITE(SORTIE, new Texture("resources/images/Exit_Z.png")),
    GEMME1(TypeDeBloc.GEMME, 0.3f, 0.3f, null,true, new Texture("resources/images/Gem_1.png")),
    GEMME2(TypeDeBloc.GEMME, 0.3f, 0.3f, null, true,new Texture("resources/images/Gem_2.png")),
    PLAYER(TypeDeBloc.PLAYER, 0.5f, 1f, new float[]{0f, -0.25f, -0.25f, 0f, 0f, 0.5f, 0.25f, 0f},false ,new Texture("resources/images/Idle__000.png")),
    EAU(TypeDeBloc.EAU, 1f, 0.75f, new float[]{-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.25f, -0.5f, 0.25f},false ,new Texture("resources/images/Water.png")),
    BRICK_A(SOL, new Texture("resources/images/Brick_A.png")),
    BRICK_B(SOL, new Texture("resources/images/Brick_B.png")),
    BRICK_C(SOL, new Texture("resources/images/Brick_C.png")),
    BRICK_D(SOL, new Texture("resources/images/Brick_D.png")),
    BRICK_E(SOL, new Texture("resources/images/Brick_E.png")),
    BRICK_F(SOL, new Texture("resources/images/Brick_F.png")),
    BRICK_G(SOL, new Texture("resources/images/Brick_G.png")),
    BRICK_H(SOL, new Texture("resources/images/Brick_H.png")),
    BRICK_I(SOL,new Texture( "resources/images/Brick_I.png"));
    //AIR(TypeDeBloc.AIR,1f,1f,null,null);


    private final TypeDeBloc type;
    private Texture[] textures;
    private final float width;
    private final float height;
    private final float[] shape;
    private boolean hasAnimation;


    UIBloc(UIBloc uiBloc, Texture texture)
    {
        this.type = uiBloc.type;
        this.width = uiBloc.width;
        this.height = uiBloc.height;
        this.shape = uiBloc.shape;
        this.hasAnimation = uiBloc.hasAnimation;
        this.textures = new Texture[1];
        textures[0] = texture;
    }

    UIBloc(TypeDeBloc type, float width, float height, float[] shape)
    {
        this.type = type;
        this.width = width;
        this.height = height;
        this.shape = shape;
    }

    UIBloc(TypeDeBloc type, float width, float height, float[] shape, boolean hasAnimation,Texture... textures)
    {
        this.type = type;
        this.width = width;
        this.height = height;
        this.shape = shape;
        this.textures = textures;
        this.hasAnimation = hasAnimation;
    }

    private BodyDef getBodyDef(float x, float y)
    {
        BodyDef body = type.getBodyDef();
        body.position.x = x+0.5f;
        body.position.y = y+0.5f;
        return body;
    }


    public Texture[] getTextures() {
        return textures;
    }

    private void addFixtures(Body body)
    {
        FixtureDef fixture = type.getFixtureDef();
        if(this.equals(GEMME1) || this.equals(GEMME2))
        {
            CircleShape circle = new CircleShape();
            circle.setRadius(width);
            fixture.shape = circle;
        }
        else if(this.equals(PLAYER))
        {
            FixtureDef diamond = type.getFixtureDef();

            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(0.125f);
            circleShape.setPosition(new Vector2(0, -0.375f));
            fixture.shape = circleShape;

            PolygonShape diamondShape = new PolygonShape();
            diamondShape.set(shape);
            diamond.shape = diamondShape;
            body.createFixture(diamond);
        }
        else
        {
            PolygonShape polShape = new PolygonShape();
            polShape.set(shape);
            fixture.shape = polShape;
        }
        body.createFixture(fixture);
    }

    public Body ajouterAuMonde(World world, float x, float y)
    {
        //if(this.equals(UIBloc.AIR)) return null;
        Body body = world.createBody(getBodyDef(x, y));
        this.addFixtures(body);
        body.setUserData(this);
        return body;
    }

    public TypeDeBloc getType() {
        return type;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public static void dispose()
    {
        for(UIBloc b : UIBloc.values())
        {
            if(b.getTextures() != null){
                for(Texture text : b.getTextures())
                    text.dispose();
            }

        }
    }

    public boolean isHasAnimation() {
        return hasAnimation;
    }
}
