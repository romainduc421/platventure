package com.mygdx.platventure.model.builder;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public enum ShapeFactory {

    SQUARE(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f),

    BREAK_LEFT(-0.5f, 0.25f, 0.5f, 0.25f, 0.5f, -0.5f, 0, -0.5f, -0.5f, -0.125f),
    BREAK_RIGHT(-0.5f, 0.25f, 0.5f, 0.25f, 0.5f, -0.125f, 0, -0.5f, -0.5f, -0.5f),

    RECTANGLE(-0.5f, -0.5f, -0.5f, 0.25f, 0.5f, -0.5f, 0.5f, 0.25f),

    DIAMOND(0f, 0.5f, 0.25f, 0f, 0f, -0.25f, -0.25f, 0f),
    Circle(0.125f, 0f, -0.375f);

    private Polygon polygon = null;
    private Circle circle = null;

    ShapeFactory(float ... vertices) {
        assert(vertices.length % 2 == 0) : "For polygon, the length of vertices must be pair";
        polygon = new Polygon();
        polygon.setVertices(vertices);
    }

    ShapeFactory(float radius, float x, float y) {
        circle = new Circle();
        circle.setRadius(radius);
        circle.setPosition(x, y);
    }

    public Shape build(float width, float height) {
        if(polygon != null) {
            return createPolygonShape(width, height);
        } else if(circle != null) {
            return createCircleShape(width);
        }

        return  null;
    }

    public PolygonShape createPolygonShape(float width, float height) {
        PolygonShape polygonShape = new PolygonShape();
        float[] tmp = polygon.getVertices();

        for(int i = 0; i < tmp.length; i++) {
            if(i % 2 == 0) {
                tmp[i] = tmp[i] * width;
            } else {
                tmp[i] = tmp[i] * height;
            }
        }

        polygonShape.set(tmp);

        return polygonShape;
    }

    public CircleShape createCircleShape(float width) {
        CircleShape circleShape = new CircleShape();

        float x = circle.x * width;
        float y = circle.y * width;

        float radius = circle.radius * width;

        circleShape.setRadius(radius);
        circleShape.setPosition(new Vector2(x, y));

        return circleShape;
    }

}
