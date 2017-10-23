package hu.bme.fitnessapplication.server;

import java.io.Serializable;

/**
 * DTOs are "flat" verisons of entities, only containing the model required on the frontend.
 */
public abstract class BaseDTO<Entity extends BaseEntity> implements Serializable {

    protected String id;

    /**
     * Recreates the entity from the dto, if possible
     * @return
     */
    public abstract Entity toEntity();

}
