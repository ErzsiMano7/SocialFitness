package hu.bme.fitnessapplication.server;

import java.util.List;
import java.util.UUID;

public interface BaseService<Entity extends BaseEntity> {

    Entity create(Entity entity);

    List<Entity> findAll();

    Entity findById(UUID id);

    Entity update(UUID id, Entity entity);

    void delete(UUID id);

    class InvalidEntityException extends RuntimeException {
        public InvalidEntityException() {
        }

        public InvalidEntityException(String message) {
            super(message);
        }

        public InvalidEntityException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidEntityException(Throwable cause) {
            super(cause);
        }

        public InvalidEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException() {
        }

        public EntityNotFoundException(String message) {
            super(message);
        }

        public EntityNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public EntityNotFoundException(Throwable cause) {
            super(cause);
        }

        public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
