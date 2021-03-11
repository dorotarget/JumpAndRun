package com.doro.jumpandrun.Sprites.PowerUps;

import com.badlogic.gdx.math.Vector2;

public class PowerUpDef {
    public Vector2 position;
    public Class<?> type;

    public PowerUpDef(Vector2 position, Class<?> type) {
        this.position = position;
        this.type = type;
    }

}


